package esmukanov.evote_system.hellgate.auth.impl;

import esmukanov.evote_system.audit.constants.AuditObjectTypes;
import esmukanov.evote_system.audit.enums.AuditAction;
import esmukanov.evote_system.audit.services.AuditService;
import esmukanov.evote_system.commons.entities.RoleEntity;
import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.hellgate.auth.AuthService;
import esmukanov.evote_system.hellgate.auth.JwtService;
import esmukanov.evote_system.hellgate.auth.RefreshTokenService;
import esmukanov.evote_system.hellgate.models.request.LoginRequest;
import esmukanov.evote_system.hellgate.models.request.RefreshTokenRequest;
import esmukanov.evote_system.hellgate.models.request.RegisterRequest;
import esmukanov.evote_system.hellgate.models.response.AuthResponse;
import esmukanov.evote_system.user_management.exceptions.UserAlreadyExistsException;
import esmukanov.evote_system.user_management.mappers.UserResponseMapper;
import esmukanov.evote_system.user_management.repositories.RoleRepository;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuditService auditService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserResponseMapper userResponseMapper;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("Starting login process for username [{}]", request.username());

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

        log.info("Login process for username [{}] success", request.username());

        return new AuthResponse(
                accessToken,
                refreshToken,
                BEARER_TOKEN_TYPE,
                3600L,
                userResponseMapper.toResponseAuth(user)
        );
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException("Такой пользователь уже существует");
        }

        RoleEntity role = roleRepository.findByRole(Role.USER)
                .orElseThrow(() -> new IllegalArgumentException("Такой роли не существует"));

        UserEntity user = new UserEntity();
        user.setFirstname(request.firstname());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(role));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedDate(LocalDateTime.now());

        UserEntity savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = refreshTokenService.createRefreshToken(savedUser);

        return new AuthResponse(
                accessToken,
                refreshToken,
                BEARER_TOKEN_TYPE,
                3600L,
                userResponseMapper.toResponseAuth(savedUser)
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
                3600L,
                userResponseMapper.toResponseAuth(user)
        );
    }

    @Override
    public void logout(RefreshTokenRequest request) {
        refreshTokenService.revoke(request.refreshToken());
    }
}
