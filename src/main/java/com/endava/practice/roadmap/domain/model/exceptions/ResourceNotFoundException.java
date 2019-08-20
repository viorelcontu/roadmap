package com.endava.practice.roadmap.domain.model.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    public static ResourceNotFoundException ofUserName(final Object userName) {
        return new ResourceNotFoundException("Resource with id: " + userName + " not found");
    }

    public ResourceNotFoundException(final String message) { super(message); }
}
