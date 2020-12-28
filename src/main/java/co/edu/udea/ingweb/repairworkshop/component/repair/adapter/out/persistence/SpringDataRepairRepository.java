package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
interface SpringDataRepairRepository extends PagingAndSortingRepository<Repair, Long>, JpaSpecificationExecutor<Repair> {
}
