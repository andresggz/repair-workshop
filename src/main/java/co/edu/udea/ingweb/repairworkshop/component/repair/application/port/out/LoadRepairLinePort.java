package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;

import javax.validation.constraints.NotNull;

public interface LoadRepairLinePort {

    RepairLine loadById(@NotNull Long id);
}
