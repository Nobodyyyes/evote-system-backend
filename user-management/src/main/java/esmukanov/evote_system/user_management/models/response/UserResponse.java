package esmukanov.evote_system.user_management.models.response;

import esmukanov.evote_system.commons.enums.Role;

public record UserResponse(
        String userId,
        String username,
        Role userRole
) {
}
