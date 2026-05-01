package esmukanov.evote_system.hellgate.controllers;

import esmukanov.evote_system.commons.models.User;
import esmukanov.evote_system.user_management.models.request.UserCreateRequest;
import esmukanov.evote_system.user_management.models.response.UserResponse;
import esmukanov.evote_system.user_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest request) {
        return userService.create(request);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.delete(userId);
    }
}
