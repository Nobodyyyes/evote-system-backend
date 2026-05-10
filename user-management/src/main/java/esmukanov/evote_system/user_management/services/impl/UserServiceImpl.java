package esmukanov.evote_system.user_management.services.impl;

import esmukanov.evote_system.commons.entities.RoleEntity;
import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.user_management.mappers.UserMapper;
import esmukanov.evote_system.user_management.models.User;
import esmukanov.evote_system.user_management.exceptions.UserAlreadyExistsException;
import esmukanov.evote_system.user_management.exceptions.UserNotFoundException;
import esmukanov.evote_system.user_management.mappers.UserResponseMapper;
import esmukanov.evote_system.user_management.models.request.UserCreateRequest;
import esmukanov.evote_system.user_management.models.request.UserUpdateRequest;
import esmukanov.evote_system.user_management.models.response.UserResponse;
import esmukanov.evote_system.user_management.repositories.RoleRepository;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import esmukanov.evote_system.user_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("Пользователь по ID [%s] не найден".formatted(userId)));
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.toModels(userRepository.findAll());
    }

    @Override
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException("Пользователь с username [%s] уже существует".formatted(request.username()));
        }

        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRoles(determineRoles(request.roles()));
        newUser.setUserStatus(UserStatus.ACTIVE);
        newUser.setCreatedDate(LocalDateTime.now());

        User saved = userMapper.toModel(userRepository.save(userMapper.toEntity(newUser)));
        return userResponseMapper.toResponse(saved);
    }

    @Override
    public UserResponse update(String userId, UserUpdateRequest request) {
        User existsUser = getUserById(userId);
        existsUser.setEmail(request.email());
        existsUser.setRoles(determineRoles(request.roles()));
        existsUser.setUserStatus(request.userStatus());

        userRepository.save(userMapper.toEntity(existsUser));
        return userResponseMapper.toResponse(existsUser);
    }

    private Set<RoleEntity> determineRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            RoleEntity userRole = roleRepository.findByRole(Role.USER)
                    .orElseThrow(() -> new IllegalStateException("Роль USER не найдена"));

            return new HashSet<>(Set.of(userRole));
        }

        Set<RoleEntity> roleEntities = new HashSet<>();

        for (Role role : roles) {
            RoleEntity existsRole = roleRepository.findByRole(role)
                    .orElseThrow(() -> new IllegalStateException("Роль %s не найдена".formatted(role)));

            roleEntities.add(existsRole);
        }

        return roleEntities;
    }

    @Override
    public void delete(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
    }
}
