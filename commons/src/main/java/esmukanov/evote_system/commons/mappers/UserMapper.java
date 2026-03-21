package esmukanov.evote_system.commons.mappers;

import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ModelMapper<User, UserEntity> {

    @Override
    public User toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .userStatus(entity.getUserStatus())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    @Override
    public UserEntity toEntity(User model) {
        if (model == null) {
            return null;
        }

        return UserEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .email(model.getEmail())
                .password(model.getPassword())
                .role(model.getRole())
                .userStatus(model.getUserStatus())
                .createdDate(model.getCreatedDate())
                .build();
    }
}
