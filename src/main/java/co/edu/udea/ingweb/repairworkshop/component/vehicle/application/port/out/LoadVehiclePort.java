package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface LoadVehiclePort {

    Vehicle findById(@NotNull Long id);

    Page<Vehicle> findByParameters(@NotNull VehicleQuerySearchCmd queryCriteria, @NotNull Pageable pageable);
}
