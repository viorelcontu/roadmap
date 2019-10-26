package com.endava.practice.roadmap.domain.model.exceptions;

import org.springframework.web.client.HttpStatusCodeException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ForbiddenException extends HttpStatusCodeException {

    public ForbiddenException() {
        super(FORBIDDEN, "Access to resource forbidden");
    }
}
