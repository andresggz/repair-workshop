package co.edu.udea.ingweb.repairworkshop.component.vehicle.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.AddRepairToVehicleUseCase;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out.LoadVehiclePort;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
@RequiredArgsConstructor
class AddRepairToVehicleService implements AddRepairToVehicleUseCase {

    private final LoadVehiclePort loadVehiclePort;

    @Override
    public Vehicle addRepair(@NotNull Long vehicleId, @NotNull Repair repairToAdd){

        Vehicle vehicleInDataBase = loadVehiclePort.findById(vehicleId);

        vehicleInDataBase.
                getRepairs()
                .add(repairToAdd);

        return vehicleInDataBase;
    }
}
