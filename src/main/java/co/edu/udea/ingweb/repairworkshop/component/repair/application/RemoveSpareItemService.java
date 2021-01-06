package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.RemoveSpareItemUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemRemoveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.BusinessException;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
class RemoveSpareItemService implements RemoveSpareItemUseCase {

    private static final String SPARE_ITEM_NOT_FOUND = "Spare item not found in this repair line";

    private static final String REPAIR_LINE_HAS_FINISHED = "Can not remove because the repair line has already finished";

    private static final String RESOURCE_NOT_FOUND = "Repair item not found";

    private final GetRepairLineQuery getRepairLineQuery;

    @Override
    public Set<SpareItem> remove(@NotNull SpareItemRemoveCmd spareItemToRemoveCmd) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(spareItemToRemoveCmd.getRepairLineId());

        repairLineHasNotFinished(repairLineInDataBase);

        SpareItem spareItemToRemove = repairLineInDataBase.getSpareItems()
                .stream()
                .filter(repair ->
                        repair.getId().equals(spareItemToRemoveCmd.getSpareItemId()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(RESOURCE_NOT_FOUND));

        repairLineInDataBase.setTotalSpareCost(repairLineInDataBase.getTotalSpareCost()
                - spareItemToRemove.getTotalCost());

        repairLineInDataBase.setTotalSparePrice(repairLineInDataBase.getTotalSparePrice()
                - spareItemToRemove.getTotalPrice());

       boolean spareItemRemoved = repairLineInDataBase.getSpareItems().
               removeIf(spareItem ->
                       spareItem.getId().equals(spareItemToRemoveCmd.getSpareItemId()));

        spareItemRemovedSuccessfully(spareItemRemoved);

        return repairLineInDataBase.getSpareItems();
    }

    private void spareItemRemovedSuccessfully(boolean spareItemRemoved) {
        if(!spareItemRemoved){
           throw new BusinessException(SPARE_ITEM_NOT_FOUND);
       }
    }

    private void repairLineHasNotFinished(RepairLine repairLineInDataBase) {
        if(Objects.nonNull(repairLineInDataBase.getFinishedAt())){
            throw new BusinessException(REPAIR_LINE_HAS_FINISHED);
        }
    }
}
