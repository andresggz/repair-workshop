package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface GetRepairLineQuery {

    RepairLine findById(@NotNull Long id);

    Set<SpareItem> findSpareItemsByRepairLineId(@NotNull Long id);
}
