package com.endava.practice.roadmap.domain.model.exceptions;

import org.springframework.web.client.HttpStatusCodeException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundException extends HttpStatusCodeException {

    public static ResourceNotFoundException ofUserName(final Object userName) {
        return new ResourceNotFoundException("User not found with username: " + userName);
    }

    public ResourceNotFoundException(final String message) {
        super(NOT_FOUND, message);
    }
}
