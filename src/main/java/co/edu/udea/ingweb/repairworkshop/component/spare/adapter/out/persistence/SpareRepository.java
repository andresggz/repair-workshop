package co.edu.udea.ingweb.repairworkshop.component.spare.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SpareRepository extends JpaRepository<Spare, Long> {
}
