package com.endava.practice.roadmap.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    public static ResourceNotFoundException ofId(final Object id) {
        return new ResourceNotFoundException("Resource with id: " + id + " not found");
    }

    public ResourceNotFoundException(final String message) { super(message); }
}
