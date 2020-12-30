package co.edu.udea.ingweb.repairworkshop.config.security.service;

import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.ResourceNotFoundException;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.in.model.UserQuerySearchCmd;
import co.edu.udea.ingweb.repairworkshop.component.user.application.port.out.LoadUserPort;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.Role;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String RESOURCE_NOT_FOUND = "User not found";

    private final LoadUserPort loadUserPort;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        User userFound = this.loadUserPort.loadByParameters(
                UserQuerySearchCmd
                        .builder()
                        .email(email)
                        .build(), PageRequest.of(0, 1))
                .stream()
                .findFirst().orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        return this.userBuilder(userFound.getEmail(), userFound.getPassword(),
                new Role[]{Role.AUTHENTICATED}, userFound.isActive());
    }

    private org.springframework.security.core.userdetails.User userBuilder(String email, String password,
                                                                           Role[] roles, boolean active){
        List<GrantedAuthority> authorities = new ArrayList<>();

        Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

        return new org.springframework.security.core.userdetails.User(email, password, active,
                true, true, true, authorities);
    }
}
