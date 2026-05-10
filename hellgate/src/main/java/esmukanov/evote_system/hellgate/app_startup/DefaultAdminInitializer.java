package esmukanov.evote_system.hellgate.app_startup;

import esmukanov.evote_system.commons.entities.RoleEntity;
import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.user_management.repositories.RoleRepository;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DefaultAdminInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        RoleEntity adminRole = createRoleIfNotExists(Role.ADMIN, "Администратор");

        if (!userRepository.existsByUsername("admin")) {
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(new HashSet<>(Set.of(adminRole)))
                    .createdDate(LocalDateTime.now())
                    .build();

            userRepository.save(admin);
        }
    }

    private RoleEntity createRoleIfNotExists(Role role, String name) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.save(
                        RoleEntity.builder()
                                .role(role)
                                .name(name)
                                .build()
                ));
    }
}
