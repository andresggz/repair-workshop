package co.edu.udea.ingweb.repairworkshop.component.user.application;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.GetUserQuery;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.LoadUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
class GetUserService implements GetUserQuery {

    private final LoadUserPort loadUserPort;

    @Override
    public User findById(@NotNull Long id) {

        User userFound = loadUserPort.findById(id);

        return userFound;
    }

    @Override
    public Page<User> findByParameters(@NotNull UserQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Page<User> usersFound = loadUserPort.findByParameters(queryCriteria, pageable);

        return usersFound;
    }
}
