package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;

import javax.validation.constraints.NotNull;

public interface FinishRepairLineUseCase {

    RepairLine finish(@NotNull Long id);
}
