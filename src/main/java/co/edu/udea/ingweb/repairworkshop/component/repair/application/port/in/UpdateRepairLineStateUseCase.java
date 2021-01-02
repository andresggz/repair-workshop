package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;

import javax.validation.constraints.NotNull;

public interface UpdateRepairLineStateUseCase {
    RepairLine update(@NotNull Long id, @NotNull RepairLineSaveCmd repairLineToUpdateCmd);
}
