package co.edu.udea.ingweb.repairworkshop.component.user.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
interface SpringDataUserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

}
