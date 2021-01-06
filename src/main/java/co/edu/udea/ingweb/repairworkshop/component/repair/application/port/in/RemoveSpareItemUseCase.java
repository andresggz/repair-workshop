package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.SpareItemRemoveCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface RemoveSpareItemUseCase {

    Set<SpareItem> remove(@NotNull SpareItemRemoveCmd spareItemToRemove);
}
