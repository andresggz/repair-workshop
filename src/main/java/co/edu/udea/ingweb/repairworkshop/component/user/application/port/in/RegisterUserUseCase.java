package co.edu.udea.ingweb.repairworkshop.component.user.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;

import javax.validation.constraints.NotNull;

public interface RegisterUserUseCase {

    User register(@NotNull UserSaveCmd userToRegisterCmd);
}
