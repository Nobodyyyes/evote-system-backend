package esmukanov.evote_system.hellgate.models.response;

public record AuthResponse(
        String token,
        String tokenType
) {
}
