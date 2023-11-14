package com.netflop.be.movie.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.awscore.exception.AwsServiceException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return Map.of("message:","Internal server error");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public Map<String, String> handleItemNotFoundException(ItemNotFoundException ex) {
        ex.printStackTrace();
        return Map.of("message:",ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AwsServiceException.class)
    public Map<String, String> handleAwsServiceException(AwsServiceException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return Map.of("message:","Internal server error");
    }
}
