package co.edu.udea.ingweb.repairworkshop.component.vehicle.application;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.LoadUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.GetVehicleQuery;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.UpdateVehicleStateUseCase;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out.UpdateVehicleStatePort;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateVehicleStateService implements UpdateVehicleStateUseCase {

    private final GetVehicleQuery getVehicleQuery;

    private final UpdateVehicleStatePort updateVehicleStatePort;

    private final LoadUserPort loadUserPort;

    @Override
    public Vehicle update(@NotNull Long id, @NotNull VehicleSaveCmd vehicleToUpdateCmd) {

        Vehicle vehicleInDataBase = getVehicleQuery.findById(id);

        Vehicle vehicleToUpdate = vehicleInDataBase.toBuilder().vehicleType(vehicleToUpdateCmd.getVehicleType())
                .licensePlate(vehicleToUpdateCmd.getLicensePlate()).maker(vehicleToUpdateCmd.getMaker())
                .model(vehicleToUpdateCmd.getModel()).build();

        Vehicle vehicleWithOwnersToUpdate = addOwners(vehicleToUpdate, vehicleToUpdateCmd);

        Vehicle vehicleUpdated = updateVehicleStatePort.update(vehicleWithOwnersToUpdate);

        return vehicleUpdated;
    }

    private Vehicle addOwners(@NotNull Vehicle vehicleToUpdate, @NotNull VehicleSaveCmd vehicleToUpdateCmd){

        Set<User> ownersToAdd = vehicleToUpdateCmd
                .getOwnerIds().stream()
                .map(loadUserPort::findById)
                .collect(Collectors.toSet());

        Vehicle vehicleWithOwners = vehicleToUpdate.toBuilder().owners(ownersToAdd).build();

        return vehicleWithOwners;
    }
}
