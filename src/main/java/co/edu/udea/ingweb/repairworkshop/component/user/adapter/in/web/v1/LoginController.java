package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserTokenResponse;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.LoginUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LoginController {

    private final LoginUserUseCase loginUserUseCase;

    @PreAuthorize("authenticated")
    @PostMapping(value = "/token")
    public ResponseEntity<UserTokenResponse> login(@AuthenticationPrincipal User activeUser){

        final String tokenCreated = loginUserUseCase.loginByEmail(activeUser.getUsername());

        UserTokenResponse userTokenToResponse = UserTokenResponse.of(tokenCreated);

        return ResponseEntity.ok(userTokenToResponse);
    }
}
