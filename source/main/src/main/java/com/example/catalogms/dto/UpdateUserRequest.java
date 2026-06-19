package com.example.catalogms.dto;

import java.util.List;

/** All fields optional; null means "leave unchanged". */
public record UpdateUserRequest(String password, Boolean enabled, List<String> roles) {
}
