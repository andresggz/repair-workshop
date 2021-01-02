package co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.application.port.in.model.VehicleQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface GetVehicleQuery {

    Vehicle findById(@NotNull Long id);

    Page<Vehicle> findByParameters(@NotNull VehicleQuerySearchCmd queryCriteria, @NotNull Pageable pageable);

    Set<Repair> findRepairsByVehicleId(@NotNull Long id);
}
