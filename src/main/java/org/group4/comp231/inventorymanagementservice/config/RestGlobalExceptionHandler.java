package org.group4.comp231.inventorymanagementservice.config;

import org.group4.comp231.inventorymanagementservice.utility.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class RestGlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> invalidRequestBody(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> validationErrorMessage = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationErrorMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(validationErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> invalidRequestBody(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
