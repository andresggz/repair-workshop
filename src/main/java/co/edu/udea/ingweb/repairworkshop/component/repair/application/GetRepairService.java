package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.LoadRepairPort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
@RequiredArgsConstructor
class GetRepairService implements GetRepairQuery {

    private final LoadRepairPort loadRepairPort;

    @Override
    public Repair findById(@NotNull Long id) {

        Repair repairFound = loadRepairPort.findById(id);

        return repairFound;
    }

    @Override
    public Page<Repair> findByParameters(@NotNull RepairQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Page<Repair> repairsFound = loadRepairPort.findByParameters(queryCriteria, pageable);

        return repairsFound;
    }

    @Override
    public Set<RepairLine> loadRepairLinesByRepairId(@NotNull Long id) {

        Repair repairFound = findById(id);

        Set<RepairLine> repairLinesFound = repairFound.getRepairLines();

        return repairLinesFound;
    }
}
