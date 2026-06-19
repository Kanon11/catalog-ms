package com.example.catalogms.dto;

import java.util.List;

/** User view without the password hash. */
public record UserResponse(Long id, String username, boolean enabled, List<String> roles) {
}
