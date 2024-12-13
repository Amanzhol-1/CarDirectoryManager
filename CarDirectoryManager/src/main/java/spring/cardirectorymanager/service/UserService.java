package spring.cardirectorymanager.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.cardirectorymanager.dto.RegisterRequest;
import spring.cardirectorymanager.enums.UserRole;
import spring.cardirectorymanager.model.User;
import spring.cardirectorymanager.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder /*, LogService logService */) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(RegisterRequest registerRequest) {
        ensureUsernameIsUnique(registerRequest.getUsername());
        Set<UserRole> roles = assignRoles(registerRequest.getRoles(), registerRequest.getUsername());
        User user = createUser(registerRequest, roles);
        User savedUser = userRepository.save(user);
        logger.info("User '{}' registered with roles {}.", savedUser.getUsername(), roles);
        return savedUser;
    }

    private void ensureUsernameIsUnique(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            logger.warn("Registration attempt with existing username: {}", username);
            throw new RuntimeException("Username is already taken!");
        });
    }

    private Set<UserRole> assignRoles(Set<String> roleStrings, String username) {
        if (roleStrings == null || roleStrings.isEmpty()) {
            logger.debug("Assigning default role USER to new user '{}'.", username);
            return Set.of(UserRole.USER);
        }
        return roleStrings.stream()
                .map(roleStr -> convertToUserRole(roleStr, username))
                .collect(Collectors.toSet());
    }

    private UserRole convertToUserRole(String roleStr, String username) {
        try {
            UserRole role = UserRole.valueOf(roleStr.toUpperCase());
            logger.debug("Assigning role {} to user '{}'.", role, username);
            return role;
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid role '{}' provided for user '{}'.", roleStr, username);
            throw new RuntimeException("Role " + roleStr + " does not exist.");
        }
    }

    private User createUser(RegisterRequest registerRequest, Set<UserRole> roles) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();
    }
}
