package co.edu.udea.ingweb.repairworkshop.component.user.application.port.in;

import javax.validation.constraints.NotNull;

public interface LoginUserUseCase {

    String loginByEmail(@NotNull String email);
}
