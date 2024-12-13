package spring.cardirectorymanager.service;

import spring.cardirectorymanager.dto.AuthResponse;
import spring.cardirectorymanager.dto.LoginRequest;
import spring.cardirectorymanager.dto.RegisterRequest;

public interface AuthService {
    void registerUser(RegisterRequest registerRequest);
    AuthResponse authenticateUser(LoginRequest loginRequest);
}

