package esmukanov.evote_system.user_management.mappers;

import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.user_management.models.User;
import esmukanov.evote_system.user_management.models.response.UserResponse;
import esmukanov.evote_system.user_management.models.response.UserResponseAuth;
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

    public UserResponseAuth toResponseAuth(UserEntity entity) {

        String role = entity.getRoles()
                .stream()
                .findFirst()
                .map(roleEntity -> roleEntity.getRole().name())
                .orElse("USER");

        return new UserResponseAuth(
                entity.getId().toString(),
                entity.getFirstname(),
                entity.getName(),
                entity.getUsername(),
                entity.getEmail(),
                role,
                entity.getStatus().name(),
                entity.getCreatedDate()
        );
    }
}
