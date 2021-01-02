package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;

import javax.validation.constraints.NotNull;

public interface UpdateVehicleStateUseCase {
    Vehicle update(@NotNull Long id, @NotNull VehicleSaveCmd vehicleToUpdateCmd);
}
