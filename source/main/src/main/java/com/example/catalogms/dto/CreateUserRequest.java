package com.example.catalogms.dto;

import java.util.List;

/** Roles are referenced by name (e.g. "MANAGER"). */
public record CreateUserRequest(String username, String password, List<String> roles) {
}
