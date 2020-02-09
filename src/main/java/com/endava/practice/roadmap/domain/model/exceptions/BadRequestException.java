package com.endava.practice.roadmap.domain.model.exceptions;

import org.springframework.web.client.HttpStatusCodeException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends HttpStatusCodeException {

    public static BadRequestException ofWrongCrypto() {
        return new BadRequestException("Crypto-currency does not exist");
    }

    public BadRequestException(final String message) {
        super(BAD_REQUEST, message);
    }
}
