package esmukanov.evote_system.user_management.services.impl;

import esmukanov.evote_system.audit.constants.AuditObjectTypes;
import esmukanov.evote_system.audit.enums.AuditAction;
import esmukanov.evote_system.audit.services.AuditService;
import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.user_management.exceptions.UserAlreadyExistsException;
import esmukanov.evote_system.user_management.exceptions.UserNotFoundException;
import esmukanov.evote_system.user_management.mappers.UserMapper;
import esmukanov.evote_system.user_management.mappers.UserResponseMapper;
import esmukanov.evote_system.user_management.models.User;
import esmukanov.evote_system.user_management.models.request.UserCreateRequest;
import esmukanov.evote_system.user_management.models.request.UserUpdateRequest;
import esmukanov.evote_system.user_management.models.response.UserResponse;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import esmukanov.evote_system.user_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final AuditService auditService;

    @Override
    public UserResponse getUserResponseById(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("Пользователь по ID [%s] не найден".formatted(userId)));

        return userResponseMapper.toResponse(user);
    }

    private User getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("Пользователь по ID [%s] не найден".formatted(userId)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userResponseMapper.toResponses(userMapper.toModels(userRepository.findAll()));
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
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setCreatedDate(LocalDateTime.now());

        User saved = userMapper.toModel(userRepository.save(userMapper.toEntity(newUser)));

        auditService.logSystemAction(
                AuditAction.USER_CREATED,
                AuditObjectTypes.USER,
                saved.getId(),
                "Создан новый пользователь"
        );

        return userResponseMapper.toResponse(saved);
    }

    @Override
    public UserResponse update(String userId, UserUpdateRequest request) {
        User existsUser = getUserById(userId);

        if (request.email() != null) {
            existsUser.setEmail(request.email());
        }

        if (request.roles() != null && !request.roles().isEmpty()) {
            existsUser.setRoles(request.roles());
        }

        if (request.userStatus() != null) {
            existsUser.setStatus(request.userStatus());
        }

        User saved = userMapper.toModel(
                userRepository.save(userMapper.toEntity(existsUser))
        );

        auditService.logSystemAction(
                AuditAction.USER_STATUS_CHANGED,
                AuditObjectTypes.USER,
                saved.getId(),
                "Обновлены данные пользователя"
        );

        return userResponseMapper.toResponse(saved);
    }

    private Set<Role> determineRoles(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Set.of(Role.USER);
        }

        return roles;
    }

    @Override
    public void delete(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
    }

    @Override
    public UserResponse changeStatus(String userId, UserStatus status) {
        User user = getUserById(userId);
        user.setStatus(status);

        User saved = userMapper.toModel(
                userRepository.save(userMapper.toEntity(user))
        );

        auditService.logSystemAction(
                AuditAction.USER_STATUS_CHANGED,
                AuditObjectTypes.USER,
                saved.getId(),
                "Изменен статус пользователя на " + status
        );

        return userResponseMapper.toResponse(saved);
    }

    @Override
    public UserResponse changeRoles(String userId, Set<Role> roles) {
        User user = getUserById(userId);
        user.setRoles(determineRoles(roles));

        User saved = userMapper.toModel(
                userRepository.save(userMapper.toEntity(user))
        );

        auditService.logSystemAction(
                AuditAction.USER_ROLES_CHANGED,
                AuditObjectTypes.USER,
                saved.getId(),
                "Изменены роли пользователя"
        );

        return userResponseMapper.toResponse(saved);
    }
}
