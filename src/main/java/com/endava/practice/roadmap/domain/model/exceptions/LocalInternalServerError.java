package com.endava.practice.roadmap.domain.model.exceptions;

public class LocalInternalServerError extends RuntimeException {

    public static LocalInternalServerError ofWrongMarketToken  () {
        return new LocalInternalServerError("Internal Data Source Authentication Error");
    }

    public static LocalInternalServerError ofUnspecified () {
        return new LocalInternalServerError("Internal Server Error");
    }

    public LocalInternalServerError(String message) {
        super(message);
    }
}
