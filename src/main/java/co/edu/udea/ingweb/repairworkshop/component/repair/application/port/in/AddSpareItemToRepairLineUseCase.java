package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface AddSpareItemToRepairLineUseCase {

    Set<SpareItem> addSpareItem(@NotNull SpareItemSaveCmd spareItemToAddCmd);
}
