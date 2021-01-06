package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.ChangeRepairStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairUpdateStateCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.LoadRepairPort;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairStatePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairState;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
@Transactional
class ChangeRepairStateService implements ChangeRepairStateUseCase {

    private static final String REPAIR_ALREADY_ENTERED = "Repair already entered";
    private static final String REPAIR_ALREADY_APPROVED = "Repair already approved previously";
    private static final String REPAIR_NOT_IN_APPROVED_STATE = "Repair must be in APPROVED state";
    private static final String REPAIR_NOT_IN_REPAIRING_STATE = "Repair must be in REPAIRING state";
    private static final String REPAIR_CAN_NOT_BE_CANCELED = "Repair already REPAIRED. Can not cancel it";

    private final LoadRepairPort loadRepairPort;

    private final UpdateRepairStatePort updateRepairStatePort;

    @Override
    public Repair update(@NotNull Long id, @NotNull RepairUpdateStateCmd repairToUpdate) {

        Repair repairInDataBase = loadRepairPort.findById(id);

        switch (repairToUpdate.getState()){
            case ENTERED:
                throw new BusinessException(REPAIR_ALREADY_ENTERED);
            case APPROVED:
                if(!repairInDataBase.getState().equals(RepairState.ENTERED)){
                    throw new BusinessException(REPAIR_ALREADY_APPROVED);
                }
                repairInDataBase.setState(RepairState.APPROVED);
                break;
            case REPAIRING:
                if(!repairInDataBase.getState().equals(RepairState.APPROVED)){
                    throw new BusinessException(REPAIR_NOT_IN_APPROVED_STATE);
                }
                repairInDataBase.setState(RepairState.REPAIRING);
                break;
            case REPAIRED:
                if(!repairInDataBase.getState().equals(RepairState.REPAIRING)){
                    throw new BusinessException(REPAIR_NOT_IN_REPAIRING_STATE);
                }

                repairInDataBase.setTotalCost(
                        repairInDataBase.getRepairLines()
                                .stream()
                                .map(RepairLine::getTotalSpareCost)
                                .reduce(0L,
                                Long::sum));

                repairInDataBase.setTotalPrice(
                        repairInDataBase.getRepairLines()
                                .stream()
                                .map(RepairLine::getTotalSparePrice)
                                .reduce(0L,
                                        Long::sum));

                repairInDataBase.setState(RepairState.REPAIRED);
                break;
            case CANCELED:
                if(repairInDataBase.getState().equals(RepairState.REPAIRED)){
                    throw new BusinessException(REPAIR_CAN_NOT_BE_CANCELED);
                }
                repairInDataBase.setState(RepairState.CANCELED);
                break;
        }

        Repair repairUpdated = updateRepairStatePort.update(repairInDataBase);
        return repairUpdated;
    }
}
