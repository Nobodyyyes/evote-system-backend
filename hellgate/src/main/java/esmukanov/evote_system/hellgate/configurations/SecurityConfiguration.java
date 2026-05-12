package esmukanov.evote_system.hellgate.configurations;

import esmukanov.evote_system.hellgate.auth.CustomAccessDeniedHandler;
import esmukanov.evote_system.hellgate.auth.CustomAuthenticationEntryPoint;
import esmukanov.evote_system.hellgate.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/refresh",
                                "/api/v1/auth/logout"
                        ).permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/v1/elections").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/elections/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/elections/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/elections/*/publish").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/v1/elections/*/options").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/elections/*/options/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/elections/*/options/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/elections/**")
                        .hasAnyRole("ADMIN", "USER", "AUDITOR")

                        .requestMatchers(HttpMethod.POST, "/api/v1/elections/*/votes")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/v1/elections/*/results/calculate").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/elections/*/results/publish").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/v1/elections/*/results")
                        .hasAnyRole("ADMIN", "USER", "AUDITOR")

                        .requestMatchers("/api/v1/blockchain/**")
                        .hasAnyRole("ADMIN", "AUDITOR")

                        .requestMatchers("/api/v1/audit/**")
                        .hasAnyRole("ADMIN", "AUDITOR")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
