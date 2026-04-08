package esmukanov.evote_system.user_management.services;

import esmukanov.evote_system.commons.enums.Role;
import esmukanov.evote_system.commons.enums.UserStatus;
import esmukanov.evote_system.commons.models.User;

import java.util.List;

public interface UserService {

    User getUserById(String userId);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getAllUsersByRole(Role userRole);

    List<User> getAllUsersByUserStatus(UserStatus userStatus);

    boolean existsByUserId(String userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User create(User newUser);

    User update(User user);

    User activate(String userId);

    User block(String userId);

    void delete(String userId);
}
