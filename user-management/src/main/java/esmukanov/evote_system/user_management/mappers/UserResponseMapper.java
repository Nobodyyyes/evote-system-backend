package esmukanov.evote_system.user_management.mappers;

import esmukanov.evote_system.commons.models.User;
import esmukanov.evote_system.user_management.models.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId().toString(),
                user.getUsername(),
                user.getRole()
        );
    }
}
