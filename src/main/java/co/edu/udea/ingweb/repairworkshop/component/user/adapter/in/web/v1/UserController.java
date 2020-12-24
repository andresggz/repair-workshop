package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserSaveRequest;
import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserTokenResponse;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.LoginUserUseCase;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.RegisterUserUseCase;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserSaveCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.Role;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import co.edu.udea.ingweb.repairworkshop.config.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @NotNull @Valid UserSaveRequest userToRegister, HttpServletRequest request){

        UserSaveCmd userToRegisterCmd = UserSaveRequest.toModel(userToRegister);

        userToRegisterCmd.setUserIdAuthenticated(getUserIdAuthenticated(request));

        User userRegistered =
                registerUserUseCase.register(userToRegisterCmd);

        URI location = fromUriString("/api/v1/users").path("/{id}")
                .buildAndExpand(userRegistered.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private Long getUserIdAuthenticated(HttpServletRequest request) {
        String token = jwtService.extractToken(request.getHeader(AUTHORIZATION));
        Long userIdAuthenticated = jwtService.userId(token);
        return userIdAuthenticated;
    }


}
