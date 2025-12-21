package com.pulse.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST APIs in the Pulse application.
 * This class handles specific exceptions and provides structured error
 * responses.
 */
@Slf4j
@RestControllerAdvice
public class PulseExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException exception) {

        Map<String, String> errors = new HashMap<>();
        exception.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        });

        return ResponseEntity.badRequest().body(errors);

    }

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(
            BusinessException ex, Locale locale) {

        String message = messageSource.getMessage(
                ex.getMessageKey(),
                ex.getArgs(),
                locale);

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

}
