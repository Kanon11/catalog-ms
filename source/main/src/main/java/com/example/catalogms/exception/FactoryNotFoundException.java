package com.example.catalogms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FactoryNotFoundException extends RuntimeException {

    public FactoryNotFoundException(Long id) {
        super("Factory not found: " + id);
    }
}
