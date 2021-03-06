package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.StartRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairState;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
class StartRepairLineService implements StartRepairLineUseCase {

    private final static String REPAIR_LINE_ALREADY_STARTED = "Repair line already started.";

    private final static String REPAIR_NOT_IN_REPAIRING_STATE = "Can not start because repair is not in REPAIRING state";

    private final GetRepairLineQuery getRepairLineQuery;

    private final GetRepairQuery getRepairQuery;

    @Override
    public RepairLine start(@NotNull Long repairLineToStart) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(repairLineToStart);

        Repair repairInDataBase = getRepairQuery.findById(repairLineInDataBase.getRepairId());

        repairNotInRepairingState(repairInDataBase);

        repairLineHasNotStarted(repairLineInDataBase);

        repairLineInDataBase.setStartedAt(LocalDateTime.now());
        repairLineInDataBase.setTotalSpareCost(0L);
        repairLineInDataBase.setTotalSparePrice(0L);

        return repairLineInDataBase;
    }

    private void repairNotInRepairingState(Repair repairInDataBase) {
        if(!repairInDataBase.getState().equals(RepairState.REPAIRING)){
            throw new BusinessException(REPAIR_NOT_IN_REPAIRING_STATE);
        }
    }

    private void repairLineHasNotStarted(RepairLine repairLineInDataBase) {
        if(Objects.nonNull(repairLineInDataBase.getStartedAt())) {
            throw new BusinessException(REPAIR_LINE_ALREADY_STARTED);
        }

    }
}
