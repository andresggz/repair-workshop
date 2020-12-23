package co.edu.udea.ingweb.repairworkshop.config.security.service;

import co.edu.udea.ingweb.repairworkshop.component.user.adapter.out.persistence.UserRepository;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.Role;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String id) throws UsernameNotFoundException {

        User userFound = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Cambiar esto...."));

        return this.userBuilder(userFound.getId(), userFound.getPassword(), new Role[]{Role.AUTHENTICATED}, userFound.isActive());
    }

    private org.springframework.security.core.userdetails.User userBuilder(Long id, String password,
                                                                           Role[] roles, boolean active){
        List<GrantedAuthority> authorities = new ArrayList<>();

        Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

        return new org.springframework.security.core.userdetails.User(String.valueOf(id), password, active,
                true, true, true, authorities);
    }
}
