package esmukanov.evote_system.hellgate.models.response;

public record AuthResponse(

        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn
) {
}
