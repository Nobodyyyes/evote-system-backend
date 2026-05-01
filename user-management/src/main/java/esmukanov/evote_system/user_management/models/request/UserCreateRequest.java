package esmukanov.evote_system.user_management.models.request;

import esmukanov.evote_system.commons.enums.Role;

public record UserCreateRequest(
        String username,
        String email,
        String password,
        Role role
) {
}
