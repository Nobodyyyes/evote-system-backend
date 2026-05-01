package esmukanov.evote_system.user_management.services.impl;

import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.commons.mappers.UserMapper;
import esmukanov.evote_system.commons.models.User;
import esmukanov.evote_system.user_management.exceptions.UserAlreadyExistsException;
import esmukanov.evote_system.user_management.exceptions.UserNotFoundException;
import esmukanov.evote_system.user_management.mappers.UserResponseMapper;
import esmukanov.evote_system.user_management.models.request.UserCreateRequest;
import esmukanov.evote_system.user_management.models.response.UserResponse;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import esmukanov.evote_system.user_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("Пользователь по ID [%s] не найден".formatted(userId)));
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
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
        newUser.setRole(request.role());
        newUser.setUserStatus(UserStatus.ACTIVE);
        newUser.setCreatedDate(LocalDateTime.now());

        User saved = userMapper.toModel(userRepository.save(userMapper.toEntity(newUser)));
        return userResponseMapper.toResponse(saved);
    }

    @Override
    public User update(User user) {
        User existsUser = getUserById(user.getId().toString());
        existsUser.setUsername(user.getUsername());
        existsUser.setEmail(user.getEmail());
        existsUser.setPassword(user.getPassword());
        existsUser.setRole(user.getRole());
        existsUser.setUserStatus(user.getUserStatus());

        userRepository.save(userMapper.toEntity(existsUser));
        return existsUser;
    }

    @Override
    public void delete(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(userMapper.toEntity(user));
    }
}
