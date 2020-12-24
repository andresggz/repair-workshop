package co.edu.udea.ingweb.repairworkshop.component.user.application.port.in;

import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserUseCase {

    Optional<String> login(@NotNull String email);
}
