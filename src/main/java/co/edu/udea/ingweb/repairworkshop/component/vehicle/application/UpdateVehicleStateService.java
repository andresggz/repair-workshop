package co.edu.udea.ingweb.repairworkshop.component.vehicle.application;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.GetVehicleQuery;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.UpdateVehicleStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out.UpdateVehicleStatePort;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateVehicleStateService implements UpdateVehicleStateUseCase {

    private final GetVehicleQuery getVehicleQuery;

    private final UpdateVehicleStatePort updateVehicleStatePort;

    @Override
    public Vehicle update(@NotNull Long id, @NotNull VehicleSaveCmd vehicleToUpdateCmd) {

        Vehicle vehicleInDataBase = getVehicleQuery.findById(id);

        Vehicle vehicleToUpdate = vehicleInDataBase.toBuilder().vehicleType(vehicleToUpdateCmd.getVehicleType())
                .licensePlate(vehicleToUpdateCmd.getLicensePlate()).maker(vehicleToUpdateCmd.getMaker())
                .model(vehicleToUpdateCmd.getModel()).build();

        Vehicle vehicleUpdated = updateVehicleStatePort.update(vehicleToUpdate);

        return vehicleUpdated;
    }
}
