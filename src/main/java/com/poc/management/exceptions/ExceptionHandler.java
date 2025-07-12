package com.poc.management.exceptions;

import com.poc.management.util.RestApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            errors.add(cv.getMessage());
        }
            return new ResponseEntity<>(RestApiResponse.builder()
                    .message(errors)
                    .errors(request.getDescription(false))
                    .timestamp(LocalDate.now())
                    .build(), HttpStatus.BAD_REQUEST
            );
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(RestApiException.class)
    public ResponseEntity<RestApiResponse<Object>> handleRestApiException(RestApiException ex, WebRequest request){
        return new ResponseEntity<>(RestApiResponse.builder()
                .message(ex.getErrors())
                .timestamp(LocalDate.now())
                .errors(request.getDescription(false))
                .build(),HttpStatus.BAD_REQUEST
        );
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<RestApiResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                                   WebRequest request){

        return new ResponseEntity<>(RestApiResponse.builder().timestamp(LocalDate.now())
                .message(ex.getMessage())
                .errors(request.getDescription(false))
                .build(), HttpStatus.BAD_REQUEST);

    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(RestApiResponse.builder()
                .message(errors)
                .errors(request.getDescription(false))
                .timestamp(LocalDate.now())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
