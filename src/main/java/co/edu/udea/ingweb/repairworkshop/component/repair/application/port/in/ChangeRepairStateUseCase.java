package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairUpdateStateCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;

import javax.validation.constraints.NotNull;

public interface ChangeRepairStateUseCase {

    Repair update(@NotNull Long id, @NotNull RepairUpdateStateCmd repairToUpdate);
}
