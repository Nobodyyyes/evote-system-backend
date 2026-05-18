package esmukanov.evote_system.hellgate.models.response;

import esmukanov.evote_system.user_management.models.response.UserResponseAuth;

public record AuthResponse(

        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        UserResponseAuth user
) {
}
