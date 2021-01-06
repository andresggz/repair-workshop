package co.edu.udea.ingweb.repairworkshop.component.user.application;

import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.ResourceNotFoundException;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.LoginUserUseCase;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.LoadUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import co.edu.udea.ingweb.repairworkshop.config.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUserUseCase {

    private static final String RESOURCE_NOT_FOUND = "User not found";

    private final LoadUserPort loadUserPort;
    private final JwtService jwtService;

    @Override
    public String loginByEmail(@NotNull String email) {

        User userFound =  this.loadUserPort.findByParameters(
                UserQuerySearchCmd
                        .builder()
                        .email(email)
                        .build(), PageRequest.of(0, 1))
                .stream()
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        String tokenCreated = jwtService.createToken(userFound.getEmail(),
                            userFound.getId(), userFound.getRole().name());

        return tokenCreated;

    }
}
