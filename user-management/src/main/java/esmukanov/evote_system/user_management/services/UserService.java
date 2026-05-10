package esmukanov.evote_system.user_management.services;

import esmukanov.evote_system.user_management.models.User;
import esmukanov.evote_system.user_management.models.request.UserCreateRequest;
import esmukanov.evote_system.user_management.models.request.UserUpdateRequest;
import esmukanov.evote_system.user_management.models.response.UserResponse;

import java.util.List;

public interface UserService {

    User getUserById(String userId);

    List<User> getAllUsers();

    UserResponse create(UserCreateRequest request);

    UserResponse update(String userId, UserUpdateRequest request);

    void delete(String userId);
}
