package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;

import javax.validation.constraints.NotNull;

public interface UpdateRepairStateUseCase {

    Repair update(@NotNull Long id, @NotNull RepairSaveCmd repairToUpdateCmd);
}
