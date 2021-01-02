package co.edu.udea.ingweb.repairworkshop.component.vehicle.application;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.GetVehicleQuery;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out.LoadVehiclePort;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetVehicleService implements GetVehicleQuery {

    private final LoadVehiclePort loadVehiclePort;

    @Override
    public Vehicle findById(@NotNull Long id) {

        Vehicle vehicleFound = loadVehiclePort.findById(id);

        return vehicleFound;
    }

    @Override
    public Page<Vehicle> findByParameters(@NotNull VehicleQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Page<Vehicle> vehiclesFound = loadVehiclePort.findByParameters(queryCriteria, pageable);

        return vehiclesFound;
    }

    @Override
    public Set<Repair> findRepairsByVehicleId(@NotNull Long id) {

        Vehicle vehicleFound = loadVehiclePort.findById(id);

        Set<Repair> repairsOfVehicleFound = vehicleFound.getRepairs();

        return repairsOfVehicleFound;
    }
}
