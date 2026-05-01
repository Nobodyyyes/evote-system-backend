package esmukanov.evote_system.user_management.services;

import esmukanov.evote_system.commons.entities.UserEntity;
import esmukanov.evote_system.commons.models.User;
import esmukanov.evote_system.user_management.models.request.UserCreateRequest;
import esmukanov.evote_system.user_management.models.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getUserById(String userId);

    Optional<UserEntity> getUserByUsername(String username);

    List<User> getAllUsers();

    UserResponse create(UserCreateRequest request);

    User update(User user);

    void delete(String userId);

    boolean existsByUsername(String username);

    void save(User user);
}
