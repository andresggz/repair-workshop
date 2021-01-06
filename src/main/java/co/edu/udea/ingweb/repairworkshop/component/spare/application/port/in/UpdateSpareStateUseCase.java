package co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.spare.application.port.in.model.SpareSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;

import javax.validation.constraints.NotNull;

public interface UpdateSpareStateUseCase {

    Spare update(@NotNull Long id, @NotNull SpareSaveCmd spareToUpdateCmd);
}
