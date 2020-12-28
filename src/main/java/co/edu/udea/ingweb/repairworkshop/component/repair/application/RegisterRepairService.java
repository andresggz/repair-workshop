package co.edu.udea.ingweb.repairworkshop.component.repair.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.RegisterRepairUseCase;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.out.RegisterRepairPort;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairState;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.LoadUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.AddRepairToVehicleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
class RegisterRepairService implements RegisterRepairUseCase {

    private final RegisterRepairPort registerRepairPort;

    private final LoadUserPort loadUserPort;

    private final AddRepairToVehicleUseCase addRepairToVehicleUseCase;

    @Override
    public Repair register(@NotNull RepairSaveCmd repairToRegisterCmd, @NotNull Long vehicleId) {

        Repair repairToRegister = RepairSaveCmd.toModel(repairToRegisterCmd);

        loadUserPort.loadById(repairToRegisterCmd.getUserIdAuthenticated());

        repairToRegister.setCreatedBy(repairToRegisterCmd.getUserIdAuthenticated());
        repairToRegister.setUpdatedBy(repairToRegisterCmd.getUserIdAuthenticated());
        repairToRegister.setOwner(loadUserPort.loadById(repairToRegisterCmd.getOwnerId()));
        repairToRegister.setState(RepairState.ENTERED);

        Repair repairWithRepairmenToRegister = addRepairmen(repairToRegister, repairToRegisterCmd);

        Repair repairRegistered = registerRepairPort.register(repairWithRepairmenToRegister);

        addRepairToVehicleUseCase.addRepair(vehicleId, repairRegistered);

        return repairRegistered;
    }

    private Repair addRepairmen(@NotNull Repair repairToRegister, @NotNull RepairSaveCmd vehicleToRegisterCmd){

        Set<User> repairmenToAdd = vehicleToRegisterCmd
                .getRepairmanIds().stream()
                .map(loadUserPort::loadById)
                .collect(Collectors.toSet());

        Repair repairWithRepairmen = repairToRegister.toBuilder().repairmen(repairmenToAdd).build();

        return repairWithRepairmen;
    }
}
