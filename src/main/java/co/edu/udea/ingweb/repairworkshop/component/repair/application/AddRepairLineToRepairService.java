package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.AddRepairLineToRepairUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
class AddRepairLineToRepairService implements AddRepairLineToRepairUseCase {

    private final GetRepairQuery getRepairQuery;

    @Override
    public Set<RepairLine> addRepairLine(@NotNull RepairLineSaveCmd repairLineToAddCmd) {

        RepairLine repairLineToAdd = RepairLineSaveCmd.toModel(repairLineToAddCmd);

        Repair repairInDataBase = getRepairQuery
                .findById(repairLineToAddCmd.getRepairId());

        repairInDataBase
                .getRepairLines()
                .add(repairLineToAdd);

        return repairInDataBase.getRepairLines();
    }
}
