package esmukanov.evote_system.user_management.services;

import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.user_management.models.request.UserCreateRequest;
import esmukanov.evote_system.user_management.models.request.UserUpdateRequest;
import esmukanov.evote_system.user_management.models.response.UserResponse;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserResponse getUserResponseById(String userId);

    List<UserResponse> getAllUsers();

    UserResponse create(UserCreateRequest request);

    UserResponse update(String userId, UserUpdateRequest request);

    void delete(String userId);

    UserResponse changeStatus(String userId, UserStatus status);

    UserResponse changeRoles(String userId, Set<Role> roles);
}
