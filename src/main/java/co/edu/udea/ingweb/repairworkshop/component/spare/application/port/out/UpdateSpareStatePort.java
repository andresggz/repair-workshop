package co.edu.udea.ingweb.repairworkshop.component.spare.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;

import javax.validation.constraints.NotNull;

public interface UpdateSpareStatePort {

    Spare update(@NotNull Spare spareToUpdate);
}
