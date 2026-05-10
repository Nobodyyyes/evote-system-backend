package esmukanov.evote_system.hellgate.auth;

import esmukanov.evote_system.commons.entities.RefreshTokenEntity;
import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.hellgate.configurations.properties.JwtProperties;
import esmukanov.evote_system.user_management.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    @Transactional
    public String createRefreshToken(UserEntity user) {
        String rawToken = UUID.randomUUID() + "." + UUID.randomUUID();

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .tokenHash(hash(rawToken))
                .user(user)
                .expiredAt(Instant.now().plus(jwtProperties.getRefreshTokenTtl()))
                .revoked(false)
                .createdAt(Instant.now())
                .build();

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }

    @Transactional
    public UserEntity validateAndGetUser(String rawToken) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByTokenHashAndRevokedFalse(hash(rawToken))
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (refreshToken.getExpiredAt().isBefore(Instant.now())) {
            refreshToken.setRevoked(true);
            throw new BadCredentialsException("Refresh token expired");
        }

        UserEntity user = refreshToken.getUser();

        if (user.getStatus() != UserStatus.ACTIVE) {
            refreshToken.setRevoked(true);
            throw new DisabledException("Учетная запись пользователя неактивна");
        }

        return user;
    }

    @Transactional
    public void revoke(String rawToken) {
        refreshTokenRepository.findByTokenHashAndRevokedFalse(hash(rawToken))
                .ifPresent(token -> token.setRevoked(true));
    }

    private String hash(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 алгоритм не доступен", e);
        }
    }
}
