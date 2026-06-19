package com.example.catalogms.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class Health {

    @GetMapping("/health")
    public String getMethodName() {
        return new String("Aplication  is +OK");
    }
}
