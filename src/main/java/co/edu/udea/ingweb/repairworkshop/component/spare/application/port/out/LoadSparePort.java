package co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface LoadSparePort {

    Spare loadById(@NotNull Long id);

    Page<Spare> loadByParameters(@NotNull SpareQuerySearchCmd queryCriteria, @NotNull Pageable pageable);
}
