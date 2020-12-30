package co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
interface SpringDataVehicleRepository extends PagingAndSortingRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

}
