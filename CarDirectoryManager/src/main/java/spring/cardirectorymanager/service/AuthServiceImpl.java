package spring.cardirectorymanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.cardirectorymanager.dto.AuthResponse;
import spring.cardirectorymanager.dto.LoginRequest;
import spring.cardirectorymanager.dto.RegisterRequest;
import spring.cardirectorymanager.security.CustomUserDetails;
import spring.cardirectorymanager.security.JwtUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserService userService,
                           JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void registerUser(RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        logger.info("User '{}' registered successfully.", registerRequest.getUsername());
    }

    @Override
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken((CustomUserDetails) authentication.getPrincipal());

            logger.info("User '{}' authenticated successfully.", loginRequest.getUsername());
            return new AuthResponse(jwt);
        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed for user '{}': {}", loginRequest.getUsername(), e.getMessage());
            throw e;
        }
    }
}

