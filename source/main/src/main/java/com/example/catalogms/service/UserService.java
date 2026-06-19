package com.example.catalogms.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.catalogms.dto.CreateUserRequest;
import com.example.catalogms.dto.UpdateUserRequest;
import com.example.catalogms.dto.UserResponse;
import com.example.catalogms.entity.Role;
import com.example.catalogms.entity.User;
import com.example.catalogms.exception.UserNotFoundException;
import com.example.catalogms.repository.RoleRepository;
import com.example.catalogms.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(UserService::toResponse).toList();
    }

    public UserResponse create(CreateUserRequest request) {
        if (request.username() == null || request.username().isBlank()
                || request.password() == null || request.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username and password are required");
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists: " + request.username());
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(true);
        user.setRoles(resolveRoles(request.roles()));
        return toResponse(userRepository.save(user));
    }

    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        if (request.enabled() != null) {
            user.setEnabled(request.enabled());
        }
        if (request.roles() != null) {
            user.setRoles(resolveRoles(request.roles()));
        }
        return toResponse(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /** Resolve role names (e.g. "ADMIN") to managed Role entities; reject unknown names. */
    private Set<Role> resolveRoles(List<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        if (roleNames == null) {
            return roles;
        }
        for (String name : roleNames) {
            Role role = roleRepository.findByName(name)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Unknown role: " + name));
            roles.add(role);
        }
        return roles;
    }

    private static UserResponse toResponse(User user) {
        List<String> roleNames = user.getRoles().stream().map(Role::getName).sorted().toList();
        return new UserResponse(user.getId(), user.getUsername(), user.isEnabled(), roleNames);
    }
}
