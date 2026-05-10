package esmukanov.evote_system.user_management.models.response;

import esmukanov.evote_system.commons.enums.Role;

import java.util.Set;

public record UserResponse(
        String userId,
        String username,
        Set<Role> roles
) {
}
