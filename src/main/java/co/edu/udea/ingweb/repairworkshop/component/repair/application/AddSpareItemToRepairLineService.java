package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.AddSpareItemToRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.BusinessException;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.GetSpareQuery;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
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
class AddSpareItemToRepairLineService implements AddSpareItemToRepairLineUseCase {

    private static final String REPAIR_LINE_HAS_NOT_STARTED = "Can not add a repair item because repair line has not started";
    private static final String REPAIR_LINE_HAS_FINISHED = "Can not add a repair item because repair line has already finished";
    private static final String NOT_STOCK_AVAILABLE = "There is not enough stock. Only %d items available";

    private final GetRepairLineQuery getRepairLineQuery;

    private final GetSpareQuery getSpareQuery;

    @Override
    public Set<SpareItem> addSpareItem(@NotNull SpareItemSaveCmd spareItemToAddCmd) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(spareItemToAddCmd.getRepairLineId());

        Spare spareInDataBase = getSpareQuery.findById(spareItemToAddCmd.getSpareId());

        repairLineHasStartedAndNotFinished(repairLineInDataBase);
        thereIsEnoughStock(spareItemToAddCmd, spareInDataBase);
        decreaseStock(spareItemToAddCmd, spareInDataBase);

        SpareItem spareItemToAdd = SpareItem.builder()
                .spare(spareInDataBase).unitCost(spareInDataBase.getUnitCost())
                .unitPrice(spareInDataBase.getUnitPrice()).quantity(spareItemToAddCmd.getQuantity())
                .build();

        spareItemToAdd.setTotalCost(spareItemToAdd.getUnitCost() * spareItemToAdd.getQuantity());
        spareItemToAdd.setTotalPrice(spareItemToAdd.getUnitPrice() * spareItemToAdd.getQuantity());


        repairLineInDataBase
                .getSpareItems()
                .add(spareItemToAdd);

        repairLineInDataBase.setTotalSparePrice(repairLineInDataBase.getTotalSparePrice() + spareItemToAdd.getTotalPrice());
        repairLineInDataBase.setTotalSpareCost(repairLineInDataBase.getTotalSpareCost() + spareItemToAdd.getTotalCost());

        return repairLineInDataBase.getSpareItems();
    }

    private void decreaseStock(SpareItemSaveCmd spareItemToAddCmd, Spare spareInDataBase) {
        spareInDataBase.setStock(spareInDataBase.getStock() - spareItemToAddCmd.getQuantity());
    }

    private void thereIsEnoughStock(SpareItemSaveCmd spareItemToAddCmd, Spare spareInDataBase) {
        if((spareInDataBase.getStock() - spareItemToAddCmd.getQuantity()) < 0){
            throw new BusinessException(String.format(NOT_STOCK_AVAILABLE, spareInDataBase.getStock()));
        }
    }

    private void repairLineHasStartedAndNotFinished(RepairLine repairLineInDataBase) {
        if(Objects.isNull(repairLineInDataBase.getStartedAt())) {
            throw new BusinessException(REPAIR_LINE_HAS_NOT_STARTED);
        }

        if (Objects.nonNull(repairLineInDataBase.getFinishedAt())){
            throw new BusinessException(REPAIR_LINE_HAS_FINISHED);
        }

    }
}
