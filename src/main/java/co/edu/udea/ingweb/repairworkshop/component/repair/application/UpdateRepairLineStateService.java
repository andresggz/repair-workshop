package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.UpdateRepairLineStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairLineStatePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateRepairLineStateService implements UpdateRepairLineStateUseCase {

    private static final String REPAIR_LINE_HAS_FINISHED = "Can not update because repair line has already finished";

    private final UpdateRepairLineStatePort updateRepairLineStatePort;

    private final GetRepairLineQuery getRepairLineQuery;

    @Override
    public RepairLine update(@NotNull Long id, @NotNull RepairLineSaveCmd repairLineToUpdateCmd) {

        RepairLine repairLineInDataBase = getRepairLineQuery.findById(id);

        repairLineHasFinished(repairLineInDataBase);

        RepairLine repairLineToUpdate = repairLineInDataBase.toBuilder()
                .description(repairLineToUpdateCmd.getDescription()).build();

        RepairLine repairLineUpdated = updateRepairLineStatePort.update(repairLineToUpdate);

        return repairLineUpdated;
    }

    private void repairLineHasFinished(RepairLine repairLineInDataBase){
        if (Objects.nonNull(repairLineInDataBase.getFinishedAt())){
            throw new BusinessException(REPAIR_LINE_HAS_FINISHED);
        }
    }
}
