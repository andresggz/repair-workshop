package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;

import javax.validation.constraints.NotNull;

public interface RegisterVehiclePort {

    Vehicle register(@NotNull Vehicle vehicleToRegister);
}
