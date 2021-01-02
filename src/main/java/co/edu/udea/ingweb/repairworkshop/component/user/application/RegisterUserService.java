package co.edu.udea.ingweb.repairworkshop.component.user.application;

import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.RegisterUserUseCase;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.RegisterUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Transactional
@Service
@RequiredArgsConstructor
class RegisterUserService implements RegisterUserUseCase {

    private final RegisterUserPort registerUserPort;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(@NotNull UserSaveCmd userToRegisterCmd) {

        User userToRegister = UserSaveCmd.toModel(userToRegisterCmd);

        userToRegister.setPassword(passwordEncoder.encode(userToRegisterCmd.getPassword()));

        User userRegistered = registerUserPort.register(userToRegister);

        return userRegistered;
    }
}
