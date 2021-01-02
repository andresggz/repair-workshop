package co.edu.udea.ingweb.repairworkshop.component.user.application;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.GetUserQuery;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.LoadUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetUserService implements GetUserQuery {

    private final LoadUserPort loadUserPort;

    @Override
    public User findById(@NotNull Long id) {

        User userLoaded = loadUserPort.findById(id);

        return userLoaded;
    }

    @Override
    public Page<User> findByParameters(@NotNull UserQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {

        Page<User> usersLoaded = loadUserPort.findByParameters(queryCriteria, pageable);

        return usersLoaded;
    }
}
