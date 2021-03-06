package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface LoadRepairPort {

    Repair findById(@NotNull Long id);

    Page<Repair> findByParameters(@NotNull RepairQuerySearchCmd queryCriteria, @NotNull Pageable pageable);
}
