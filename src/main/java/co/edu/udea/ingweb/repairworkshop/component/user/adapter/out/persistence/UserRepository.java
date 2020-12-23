package co.edu.udea.ingweb.repairworkshop.component.user.adapter.out.persistence;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends JpaRepository<User, Long> {
}
