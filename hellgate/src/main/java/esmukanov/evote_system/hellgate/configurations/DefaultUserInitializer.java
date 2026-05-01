package esmukanov.evote_system.hellgate.configurations;

import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DefaultUserInitializer {

    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner createDefaultAdmin() {
        return args -> {
            if (userRepository.existsByUsername("admin")) {
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password("admin")
                        .role(Role.ADMIN)
                        .createdDate(LocalDateTime.now())
                        .build();

                userRepository.save(user);
            }
        };
    }
}
