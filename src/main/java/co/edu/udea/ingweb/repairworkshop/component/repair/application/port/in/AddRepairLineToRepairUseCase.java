package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface AddRepairLineToRepairUseCase {

    Set<RepairLine> addRepairLine(@NotNull RepairLineSaveCmd repairLineToAddCmd);
}
