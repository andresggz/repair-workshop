package co.edu.udea.ingweb.repairworkshop.component.user.application;

import co.edu.udea.ingweb.repairworkshop.component.user.adapter.out.persistence.UserRepository;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.UserUseCase;
import co.edu.udea.ingweb.repairworkshop.config.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public Optional<String> login(@NotNull String email) {
        return this.userRepository.findByPrimaryEmailAddress(email)
                .map(user -> jwtService.createToken(user.getPrimaryEmailAddress(),
                        user.getId(), user.getRole().name()));
    }
}
