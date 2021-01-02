package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairLineQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.LoadRepairLinePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetRepairLineService implements GetRepairLineQuery {

    private final LoadRepairLinePort loadRepairLinePort;

    @Override
    public RepairLine findById(@NotNull Long id) {

        RepairLine repairLineLoaded = loadRepairLinePort.findById(id);

        return repairLineLoaded;
    }
}
