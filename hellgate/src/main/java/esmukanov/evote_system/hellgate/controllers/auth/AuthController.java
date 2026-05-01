package esmukanov.evote_system.hellgate.controllers.auth;

import esmukanov.evote_system.hellgate.auth.AuthService;
import esmukanov.evote_system.hellgate.models.request.LoginRequest;
import esmukanov.evote_system.hellgate.models.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
