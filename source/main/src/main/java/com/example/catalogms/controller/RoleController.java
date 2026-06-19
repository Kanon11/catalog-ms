package com.example.catalogms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogms.entity.Role;
import com.example.catalogms.repository.RoleRepository;

/** Admin-only view of the available roles. Access is enforced centrally in SecurityConfig. */
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public List<Role> all() {
        return roleRepository.findAll();
    }
}
