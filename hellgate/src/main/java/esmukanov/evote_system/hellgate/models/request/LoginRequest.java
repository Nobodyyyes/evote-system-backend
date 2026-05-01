package esmukanov.evote_system.hellgate.models.request;

public record LoginRequest(
        String username,
        String password
) {
}
