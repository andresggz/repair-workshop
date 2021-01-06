package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;

import javax.validation.constraints.NotNull;

public interface UpdateRepairLineStatePort {

    RepairLine update(@NotNull RepairLine repairLineToUpdate);
}
