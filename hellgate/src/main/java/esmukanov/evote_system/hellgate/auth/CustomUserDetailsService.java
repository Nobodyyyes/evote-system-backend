package esmukanov.evote_system.hellgate.auth;

import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new DisabledException("Учетная запись пользователя неактивна");
        }

        List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole().name()))
                .toList();

        return new User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}
