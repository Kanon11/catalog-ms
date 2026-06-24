package com.example.catalogms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkerNotFoundException extends RuntimeException {

    public WorkerNotFoundException(Long id) {
        super("Worker not found: " + id);
    }
}
