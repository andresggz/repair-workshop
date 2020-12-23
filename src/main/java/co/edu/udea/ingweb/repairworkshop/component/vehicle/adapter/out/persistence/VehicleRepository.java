package co.edu.udea.ingweb.repairworkshop.component.vehicle.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.vehicle.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
