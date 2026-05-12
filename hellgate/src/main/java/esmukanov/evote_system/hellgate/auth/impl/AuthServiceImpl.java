package esmukanov.evote_system.hellgate.auth.impl;

import esmukanov.evote_system.audit.constants.AuditObjectTypes;
import esmukanov.evote_system.audit.enums.AuditAction;
import esmukanov.evote_system.audit.services.AuditService;
import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.hellgate.auth.AuthService;
import esmukanov.evote_system.hellgate.auth.JwtService;
import esmukanov.evote_system.hellgate.auth.RefreshTokenService;
import esmukanov.evote_system.hellgate.models.request.LoginRequest;
import esmukanov.evote_system.hellgate.models.request.RefreshTokenRequest;
import esmukanov.evote_system.hellgate.models.response.AuthResponse;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuditService auditService;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String username = authentication.getName();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        auditService.logUserAction(
                user.getId(),
                user.getUsername(),
                AuditAction.USER_LOGIN_SUCCESS,
                AuditObjectTypes.USER,
                user.getId(),
                "Пользователь успешно вошел в систему"
        );

        return new AuthResponse(
                accessToken,
                refreshToken,
                BEARER_TOKEN_TYPE,
                jwtService.getAccessTokenTtlSeconds()
        );
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        UserEntity user = refreshTokenService.validateAndGetUser(request.refreshToken());
        refreshTokenService.revoke(request.refreshToken());

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                BEARER_TOKEN_TYPE,
                jwtService.getAccessTokenTtlSeconds()
        );
    }

    @Override
    public void logout(RefreshTokenRequest request) {
        refreshTokenService.revoke(request.refreshToken());
    }
}
