package co.edu.udea.ingweb.repairworkshop.component.vehicle.application;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.LoadUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.RegisterVehicleUseCase;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out.RegisterVehiclePort;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
class RegisterVehicleService implements RegisterVehicleUseCase {

    private final RegisterVehiclePort registerVehiclePort;

    private final LoadUserPort loadUserPort;

    @Override
    public Vehicle register(@NotNull VehicleSaveCmd vehicleToRegisterCmd) {

        Vehicle vehicleToRegister = VehicleSaveCmd.toModel(vehicleToRegisterCmd);

        Vehicle vehicleWithOwnersToRegister = addOwners(vehicleToRegister, vehicleToRegisterCmd);

        Vehicle vehicleRegistered = registerVehiclePort.register(vehicleWithOwnersToRegister);

        return vehicleRegistered;
    }

    private Vehicle addOwners(@NotNull Vehicle vehicleToRegister, @NotNull VehicleSaveCmd vehicleToRegisterCmd){

        Set<User> ownersToAdd = vehicleToRegisterCmd
                .getOwnerIds().stream()
                .map(loadUserPort::findById)
                .collect(Collectors.toSet());

        Vehicle vehicleWithOwners = vehicleToRegister.toBuilder().owners(ownersToAdd).build();

        return vehicleWithOwners;
    }
}
