package co.edu.udea.ingweb.repairworkshop.component.user.application.port.out;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;

import javax.validation.constraints.NotNull;

public interface RegisterUserPort {

    User register(@NotNull User userToRegister);
}
