package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.LoadRepairPort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetRepairService implements GetRepairQuery {

    private final LoadRepairPort loadRepairPort;

    @Override
    public Repair findById(@NotNull Long id) {

        Repair repairLoaded = loadRepairPort.loadById(id);

        return repairLoaded;
    }

    @Override
    public Page<Repair> findByParameters(@NotNull RepairQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Page<Repair> repairsLoaded = loadRepairPort.loadByParameters(queryCriteria, pageable);

        return repairsLoaded;
    }
}
