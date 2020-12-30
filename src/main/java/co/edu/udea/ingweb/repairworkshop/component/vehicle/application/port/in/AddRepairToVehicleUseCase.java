package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;

import javax.validation.constraints.NotNull;

public interface AddRepairToVehicleUseCase {

    Vehicle addRepair(@NotNull Long vehicleId, @NotNull Repair repairToAdd);
}
