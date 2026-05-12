package esmukanov.evote_system.user_management.mappers;

import esmukanov.evote_system.user_management.models.User;
import esmukanov.evote_system.user_management.models.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResponseMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId().toString(),
                user.getUsername(),
                user.getRoles()
        );
    }

    public List<UserResponse> toResponses(List<User> users) {
        return users.stream().map(this::toResponse).toList();
    }
}
