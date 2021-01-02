package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.StartRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairLineStatePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
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

    @Override
    public RepairLine start(@NotNull Long repairLineToStart) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(repairLineToStart);

        repairLineHasNotStarted(repairLineInDataBase);

        RepairLine repairLineToUpdate = repairLineInDataBase.toBuilder()
                .startedAt(LocalDateTime.now())
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
