package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.StartRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairLineStatePort;
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

    private final GetRepairLineQuery getRepairLineQuery;

    private final UpdateRepairLineStatePort updateRepairLineStatePort;

    private final GetRepairQuery getRepairQuery;

    @Override
    public RepairLine start(@NotNull Long repairLineToStart) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(repairLineToStart);

        Repair repairInDataBase = getRepairQuery.findById(repairLineInDataBase.getRepairId());

        if(!repairInDataBase.getState().equals(RepairState.REPAIRING)){
            throw new BusinessException("Can not start because repair is not in REPAIRING state");
        }

        repairLineHasNotStarted(repairLineInDataBase);

        RepairLine repairLineToUpdate = repairLineInDataBase.toBuilder()
                .startedAt(LocalDateTime.now())
                .totalSpareCost(0L)
                .totalSparePrice(0L)
                .build();

        RepairLine repairLineStarted = updateRepairLineStatePort.update(repairLineToUpdate);

        return repairLineStarted;
    }

    private void repairLineHasNotStarted(RepairLine repairLineInDataBase) {
        if(Objects.nonNull(repairLineInDataBase.getStartedAt())) {
            throw new BusinessException(REPAIR_LINE_ALREADY_STARTED);
        }

    }
}
