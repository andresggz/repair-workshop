package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;

import javax.validation.constraints.NotNull;

public interface RegisterVehicleUseCase {

    Vehicle register(@NotNull VehicleSaveCmd vehicleToRegisterCmd);
}
