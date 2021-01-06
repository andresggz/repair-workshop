package co.edu.udea.ingweb.repairworkshop.component.spare.application;

import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.GetSpareQuery;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.UpdateSpareStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out.UpdateSpareStatePort;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateSpareStateService implements UpdateSpareStateUseCase {

    private final GetSpareQuery getSpareQuery;

    private final UpdateSpareStatePort updateSpareStatePort;

    @Override
    public Spare update(@NotNull Long id, @NotNull SpareSaveCmd spareToUpdateCmd) {

        Spare spareInDataBase = getSpareQuery.findById(id);

        Spare spareToUpdate = spareInDataBase.toBuilder().name(spareToUpdateCmd.getName())
                .description(spareToUpdateCmd.getDescription()).stock(spareToUpdateCmd.getStock())
                .unitCost(spareToUpdateCmd.getUnitCost()).unitPrice(spareToUpdateCmd.getUnitPrice())
                .build();

        Spare spareUpdated = updateSpareStatePort.update(spareToUpdate);
        return spareUpdated;
    }
}
