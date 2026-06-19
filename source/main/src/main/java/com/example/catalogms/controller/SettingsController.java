package com.example.catalogms.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin-only application settings. Minimal in-memory key/value store — a placeholder that
 * backs the "Settings" menu item; persist to a table later if needed. Access is enforced
 * centrally in SecurityConfig.
 */
@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final Map<String, String> settings = new ConcurrentHashMap<>(Map.of(
            "catalog.currency", "USD",
            "catalog.itemsPerPage", "20"));

    @GetMapping
    public Map<String, String> all() {
        return settings;
    }

    /** Merge the supplied key/values into the current settings and return the result. */
    @PutMapping
    public Map<String, String> update(@RequestBody Map<String, String> updates) {
        settings.putAll(updates);
        return settings;
    }
}
