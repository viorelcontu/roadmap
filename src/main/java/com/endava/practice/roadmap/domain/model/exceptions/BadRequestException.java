package com.endava.practice.roadmap.domain.model.exceptions;

public class BadRequestException extends RuntimeException {

    public static BadRequestException ofMismatchedPath (Object pathId, Object resourceId) {
        String errorMsg = String.format("Mismatched URL path id: %s and resource id: %s", pathId, resourceId);
        return new BadRequestException(errorMsg);
    }

    public static BadRequestException ofWrongCrypto (Object currencyId) {
        String errorMsg = String.format("Cryptocurrency with identifier: %s does not exist", currencyId);
        return new BadRequestException(errorMsg);
    }

    private BadRequestException(final String message) { super(message); }
}
