package esmukanov.evote_system.user_management.models.response;

import java.time.LocalDateTime;

public record UserResponseAuth(

        String id,
        String firstname,
        String name,
        String username,
        String email,
        String role,
        String status,
        LocalDateTime createdAt
) {
}
