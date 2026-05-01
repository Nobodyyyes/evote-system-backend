package esmukanov.evote_system.hellgate.auth;

import esmukanov.evote_system.hellgate.models.request.LoginRequest;
import esmukanov.evote_system.hellgate.models.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);
}
