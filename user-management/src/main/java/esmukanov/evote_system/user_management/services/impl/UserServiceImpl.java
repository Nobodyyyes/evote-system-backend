package esmukanov.evote_system.user_management.services.impl;

import esmukanov.evote_system.commons.mappers.UserMapper;
import esmukanov.evote_system.commons.models.User;
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
        return List.of();
    }

    @Override
    public User create(User newUser) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(String userId) {

    }
}
