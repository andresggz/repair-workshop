package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RepairRepository extends JpaRepository<Repair, Long> {
}
