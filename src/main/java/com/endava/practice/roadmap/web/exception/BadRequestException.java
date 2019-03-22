package com.endava.practice.roadmap.web.exception;

public class BadRequestException extends RuntimeException {

    public static BadRequestException ofMismatchedPath (Object pathId, Object resourceId) {
        String errorMsg = String.format("Mismatched URL path id: %s and resource id: %s", pathId, resourceId);
        return new BadRequestException(errorMsg);
    }

    private BadRequestException(final String message) { super(message); }
}
