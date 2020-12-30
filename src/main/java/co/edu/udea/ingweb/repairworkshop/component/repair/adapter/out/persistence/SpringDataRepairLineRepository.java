package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SpringDataRepairLineRepository extends PagingAndSortingRepository<RepairLine, Long> {
}
