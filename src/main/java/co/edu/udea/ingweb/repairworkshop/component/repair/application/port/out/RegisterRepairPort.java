package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;

import javax.validation.constraints.NotNull;

public interface RegisterRepairPort {

    Repair register(@NotNull Repair repairToRegister);
}
