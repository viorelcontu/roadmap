package com.endava.practice.roadmap.domain.model.exceptions;

public class BadRequestException extends RuntimeException {

    public static BadRequestException ofWrongCrypto (Object currencyId) {
        String errorMsg = String.format("Cryptocurrency with identifier: %s does not exist", currencyId);
        return new BadRequestException(errorMsg);
    }

    public BadRequestException(final String message) { super(message); }
}
