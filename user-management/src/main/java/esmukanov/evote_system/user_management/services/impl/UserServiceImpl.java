package esmukanov.evote_system.user_management.services.impl;

import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.commons.mappers.UserMapper;
import esmukanov.evote_system.commons.models.User;
import esmukanov.evote_system.user_management.exceptions.UserAlreadyExistsException;
import esmukanov.evote_system.user_management.exceptions.UserNotFoundException;
import esmukanov.evote_system.user_management.repositories.UserRepository;
import esmukanov.evote_system.user_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("User by ID [%s] not found".formatted(userId)));
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.toModels(userRepository.findAll());
    }

    @Override
    public User create(User newUser) {
        if (userRepository.existsById(newUser.getId())) {
            throw new UserAlreadyExistsException("User by ID [%s] already exists".formatted(newUser.getId()));
        }

        userRepository.save(userMapper.toEntity(newUser));
        return newUser;
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
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("User by username [%s] not found".formatted(username)));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException("User by email [%s] not found".formatted(email)));
    }

    @Override
    public List<User> getAllUsersByRole(Role userRole) {
        return List.of();
    }

    @Override
    public List<User> getAllUsersByUserStatus(UserStatus userStatus) {
        return List.of();
    }

    @Override
    public boolean existsByUserId(String userId) {
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public User activate(String userId) {
        return null;
    }

    @Override
    public User block(String userId) {
        return null;
    }
}
