package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.FinishRepairLineUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
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
class FinishRepairLineService implements FinishRepairLineUseCase {

    private final static String REPAIR_LINE_ALREADY_FINISHED = "Repair line already finished";

    private final static String REPAIR_LINE_HAS_NOT_STARTED = "Repair line has not started";

    private final GetRepairLineQuery getRepairLineQuery;

    private final UpdateRepairLineStatePort updateRepairLineStatePort;

    @Override
    public RepairLine finish(@NotNull Long id) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(id);

        repairLineHasStarted(repairLineInDataBase);

        repairLineHasNotFinished(repairLineInDataBase);

        RepairLine repairLineToUpdate = repairLineInDataBase.toBuilder()
                .finishedAt(LocalDateTime.now())
                .build();

        RepairLine repairLineFinished = updateRepairLineStatePort.update(repairLineToUpdate);

        return repairLineFinished;
    }

    private void repairLineHasNotFinished(RepairLine repairLineInDataBase) {
        if(Objects.nonNull(repairLineInDataBase.getFinishedAt())){
            throw new BusinessException(REPAIR_LINE_ALREADY_FINISHED);
        }
    }

    private void repairLineHasStarted(RepairLine repairLineInDataBase){
        if(Objects.isNull(repairLineInDataBase.getStartedAt())){
            throw new BusinessException(REPAIR_LINE_HAS_NOT_STARTED);
        }
    }
}
