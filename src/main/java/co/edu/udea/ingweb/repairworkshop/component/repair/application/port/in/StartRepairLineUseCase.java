package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;

import javax.validation.constraints.NotNull;

public interface StartRepairLineUseCase {

    RepairLine start(@NotNull Long repairLineIdToStart);
}
