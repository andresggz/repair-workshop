package co.edu.udea.ingweb.repairworkshop.component.user.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface GetUserQuery {

    User findById(@NotNull Long id);

    Page<User> findByParameters(@NotNull UserQuerySearchCmd queryCriteria, @NotNull Pageable pageable);
}
