package co.edu.udea.ingweb.repairworkshop.component.vehicle.application;

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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetVehicleService implements GetVehicleQuery {

    private final LoadVehiclePort loadVehiclePort;

    @Override
    public Vehicle findById(@NotNull Long id) {

        Vehicle vehicleLoaded = loadVehiclePort.findById(id);

        return vehicleLoaded;
    }

    @Override
    public Page<Vehicle> findByParameters(@NotNull VehicleQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Page<Vehicle> vehiclesLoaded = loadVehiclePort.findByParameters(queryCriteria, pageable);

        return vehiclesLoaded;
    }
}
