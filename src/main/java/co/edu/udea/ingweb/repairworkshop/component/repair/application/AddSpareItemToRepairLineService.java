package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.AddSpareItemToRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairLineStatePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.BusinessException;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.GetSpareQuery;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out.UpdateSpareStatePort;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.GetUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    private final GetUserQuery getUserQuery;

    private final UpdateRepairLineStatePort updateRepairLineStatePort;

    private final UpdateSpareStatePort updateSpareStatePort;

    @Override
    public Set<SpareItem> addSpareItem(@NotNull SpareItemSaveCmd spareItemToAddCmd) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(spareItemToAddCmd.getRepairLineId());

        Spare spareInDataBase = getSpareQuery.findById(spareItemToAddCmd.getSpareId());

        thereIsEnoughStock(spareItemToAddCmd, spareInDataBase);

        Spare spareStockToUpdate = spareInDataBase.toBuilder()
                .stock(spareInDataBase.getStock() - spareItemToAddCmd.getQuantity()).build();

        updateSpareStatePort.update(spareStockToUpdate);

        SpareItem spareItemToAdd = SpareItem.builder()
                .spare(spareInDataBase).unitCost(spareInDataBase.getUnitCost())
                .unitPrice(spareInDataBase.getUnitPrice()).quantity(spareItemToAddCmd.getQuantity())
                .updatedAt(LocalDateTime.now()).createdAt(LocalDateTime.now())
                .build();

      SpareItem spareItemToBeAdded = spareItemToAdd.toBuilder()
              .totalCost(spareItemToAdd.getUnitCost() * spareItemToAdd.getQuantity())
              .totalPrice(spareItemToAdd.getUnitPrice() * spareItemToAdd.getQuantity())
              .build();

      Set<SpareItem> spareItemsWithNewSpareItem = repairLineInDataBase
              .getSpareItems();

        spareItemsWithNewSpareItem.add(spareItemToBeAdded);

      RepairLine repairLineToUpdate = repairLineInDataBase.toBuilder()
              .spareItems(spareItemsWithNewSpareItem).build();

      repairLineHasStartedAndNotFinished(repairLineInDataBase);

      RepairLine repairLineUpdated = updateRepairLineStatePort.update(repairLineToUpdate);

        return repairLineUpdated.getSpareItems();
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
