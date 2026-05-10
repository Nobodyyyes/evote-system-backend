package esmukanov.evote_system.user_management.mappers;

import esmukanov.evote_system.commons.entities.RoleEntity;
import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.mappers.ModelMapper;
import esmukanov.evote_system.user_management.models.User;
import esmukanov.evote_system.user_management.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper implements ModelMapper<User, UserEntity> {

    private final RoleRepository roleRepository;

    @Override
    public User toModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        Set<Role> roles = entity.getRoles() == null ? Set.of() : entity.getRoles()
                                                                 .stream()
                                                                 .map(RoleEntity::getRole)
                                                                 .collect(Collectors.toSet());

        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(roles)
                .userStatus(entity.getStatus())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    @Override
    public UserEntity toEntity(User model) {
        if (model == null) {
            return null;
        }

        Set<RoleEntity> roleEntities = model.getRoles() == null
                ? new HashSet<>()
                : model.getRoles().stream()
                  .map(this::getRoleEntity)
                  .collect(Collectors.toSet());

        return UserEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .email(model.getEmail())
                .password(model.getPassword())
                .roles(roleEntities)
                .userStatus(model.getUserStatus())
                .createdDate(model.getCreatedDate())
                .build();
    }

    private RoleEntity getRoleEntity(Role role) {
        return roleRepository.findByRole(role)
                .orElseThrow(() -> new IllegalStateException("Роль не найдена: " + role));
    }
}
