package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.shared.model.ErrorDetails;
import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserTokenResponse;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.LoginUserUseCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = {"Login"}, value = "Login")
public class LoginController {

    private final LoginUserUseCase loginUserUseCase;

    @PreAuthorize("authenticated")
    @PostMapping(value = "/token")
    @ApiOperation(value = "Obtain a token.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = UserTokenResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorDetails.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorDetails.class)

    })
    public ResponseEntity<UserTokenResponse> login(@AuthenticationPrincipal User activeUser){

        final String tokenCreated = loginUserUseCase.loginByEmail(activeUser.getUsername());

        UserTokenResponse userTokenToResponse = UserTokenResponse.of(tokenCreated);

        return ResponseEntity.ok(userTokenToResponse);
    }
}
