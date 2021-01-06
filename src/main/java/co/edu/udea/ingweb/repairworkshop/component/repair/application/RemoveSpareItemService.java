package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.RemoveSpareItemUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemRemoveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairLineStatePort;
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

    private final GetRepairLineQuery getRepairLineQuery;

    private final UpdateRepairLineStatePort updateRepairLineStatePort;

    @Override
    public Set<SpareItem> remove(@NotNull SpareItemRemoveCmd spareItemToRemoveCmd) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(spareItemToRemoveCmd.getRepairLineId());

        repairLineHasNotFinished(repairLineInDataBase);

        SpareItem spareItemToRemove = repairLineInDataBase.getSpareItems()
                .stream()
                .filter(repair ->
                        repair.getId().equals(spareItemToRemoveCmd.getSpareItemId()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Repair item not found"));

        repairLineInDataBase.setTotalSpareCost(repairLineInDataBase.getTotalSpareCost()
                - spareItemToRemove.getTotalCost() * spareItemToRemove.getQuantity());

        repairLineInDataBase.setTotalSpareCost(repairLineInDataBase.getTotalSparePrice()
                - spareItemToRemove.getTotalPrice() * spareItemToRemove.getQuantity());

       boolean spareItemRemoved = repairLineInDataBase.getSpareItems().
               removeIf(spareItem -> spareItem.getId().equals(spareItemToRemoveCmd.getSpareItemId()));

        spareItemRemovedSuccessfully(spareItemRemoved);

        RepairLine repairLineUpdated = updateRepairLineStatePort.update(repairLineInDataBase);

        Set<SpareItem> spareItemsWithoutSpareItemRemoved = repairLineUpdated.getSpareItems();

        return spareItemsWithoutSpareItemRemoved;
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
