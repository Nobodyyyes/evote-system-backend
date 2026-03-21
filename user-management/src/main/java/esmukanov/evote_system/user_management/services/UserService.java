package esmukanov.evote_system.user_management.services;

import esmukanov.evote_system.commons.models.User;

import java.util.List;

public interface UserService {

    User getUserById(String userId);

    List<User> getAllUsers();

    User create(User newUser);

    User update(User user);

    void delete(String userId);
}
