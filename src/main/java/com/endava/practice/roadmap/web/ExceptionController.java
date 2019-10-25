package com.endava.practice.roadmap.web;

import com.endava.practice.roadmap.domain.model.exceptions.BadRequestException;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.internal.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler (value = HttpStatusCodeException.class)
    public ResponseEntity<Object> handleResourceNotFound(final HttpStatusCodeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, apiMessage(ex), new HttpHeaders(), ex.getStatusCode(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers,
                                                                  final HttpStatus status, final WebRequest request) {
        log.debug("Submitted a malformed json");
        return handleExceptionInternal(ex, apiMessage(BAD_REQUEST, ex), headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers,
                                                                  final HttpStatus status, final WebRequest request) {
        log.debug("Submitted a JSON with unexpected property");
        return handleExceptionInternal(ex, apiMessage(BAD_REQUEST, ex), headers, BAD_REQUEST, request);
    }

    private ApiStatus apiMessage(final HttpStatusCodeException ex) {
        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        final String devMessage = ExceptionUtils.getRootCauseMessage(ex);
        return new ApiStatus(ex.getStatusCode().toString(), message, devMessage);
    }

    private ApiStatus apiMessage(final HttpStatus httpStatus, final Exception ex) {
        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        final String devMessage = ExceptionUtils.getRootCauseMessage(ex);
        return new ApiStatus(httpStatus.toString(), message, devMessage);
    }
}
