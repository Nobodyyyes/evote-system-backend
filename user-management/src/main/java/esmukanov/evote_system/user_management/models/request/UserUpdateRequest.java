package esmukanov.evote_system.user_management.models.request;

import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;

import java.util.Set;

public record UserUpdateRequest(

        String email,
        Set<Role> roles,
        UserStatus userStatus
) {
}
