package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.GetRepairQuery;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.UpdateRepairStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.UpdateRepairStatePort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateRepairStateService implements UpdateRepairStateUseCase {

    private final GetRepairQuery getRepairQuery;

    private final UpdateRepairStatePort updateRepairStatePort;

    @Override
    public Repair update(@NotNull Long id, @NotNull RepairSaveCmd repairToUpdateCmd) {

        Repair repairInDataBase = getRepairQuery.findById(id);

        Repair repairToUpdate = repairInDataBase.toBuilder().commentary(repairToUpdateCmd.getCommentary())
                .build();

        Repair repairUpdated = updateRepairStatePort.update(repairToUpdate);

        return repairUpdated;
    }
}
