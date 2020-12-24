package co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1;

import co.edu.udea.ingweb.repairworkshop.component.user.adapter.in.web.v1.model.UserTokenResponse;
import co.edu.udea.ingweb.repairworkshop.component.user.adapter.out.persistence.UserRepository;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.UserUseCase;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> test(String username){

        return ResponseEntity.ok(username);
    }

    @PreAuthorize("authenticated")
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello!!!");
    }

    @PreAuthorize("hasRole('GERENTE_GENERAL')")
    @GetMapping("/gerente")
    String test1(){
        return "yes gerente general";
    }

    @GetMapping("createUser")
    public ResponseEntity<co.edu.udea.ingweb.repairworkshop.component.user.domain.User> create(){
        System.out.println("yes");
       return ResponseEntity.ok(userRepository.save(co.edu.udea.ingweb.repairworkshop.component.user.domain.User.builder()
        .names("andres").lastNames("grisales").primaryEmailAddress("andres@gmail.com").active(true)
        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).dni("243435455").phoneNumber("4344343")
               .password(new BCryptPasswordEncoder().encode("fdfdfdfdew"))
        .profilePhoto("fdfdfdf.com").role(Role.ASISTENTE_FINANCIERO).build()));
    }


    @PreAuthorize("authenticated")
    @PostMapping(value = "/token")
    public ResponseEntity<Optional<UserTokenResponse>> login(@AuthenticationPrincipal User activeUser){

        return ResponseEntity.ok(userUseCase.login(activeUser.getUsername())
                .map(UserTokenResponse::new));
    }

}
