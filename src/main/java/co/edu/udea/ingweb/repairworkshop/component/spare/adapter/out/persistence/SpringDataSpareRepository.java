package co.edu.udea.ingweb.repairworkshop.component.spare.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.spare.domain.Spare;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
interface SpringDataSpareRepository extends PagingAndSortingRepository<Spare, Long>, JpaSpecificationExecutor<Spare> {

}
