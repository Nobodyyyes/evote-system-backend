package esmukanov.evote_system.hellgate.auth.impl;

import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.mappers.UserMapper;
import esmukanov.evote_system.hellgate.auth.AuthService;
import esmukanov.evote_system.hellgate.auth.JwtService;
import esmukanov.evote_system.hellgate.models.request.LoginRequest;
import esmukanov.evote_system.hellgate.models.response.AuthResponse;
import esmukanov.evote_system.user_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    private final UserMapper userMapper;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserEntity user = userService.getUserByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        String token = jwtService.generateToken(userMapper.toModel(user));

        return new AuthResponse(token, BEARER_TOKEN_TYPE);
    }
}
