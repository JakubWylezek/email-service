package com.taskservice.emailservice.exception;

import com.taskservice.emailservice.exception.custom.DuplicateMailException;
import com.taskservice.emailservice.exception.custom.EmailNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        return buildResponseEntity(ApiError.builder()
                .message("Invalid request")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        return buildResponseEntity(ApiError.builder()
                .message(ex.getParameterName() + " parameter is missing")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        return buildResponseEntity(ApiError.builder()
                .message("Invalid request method")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    protected ResponseEntity<Object> handleEmailNotFoundException(EmailNotFoundException ex) {
        return buildResponseEntity(ApiError.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND).build());
    }

    @ExceptionHandler(DuplicateMailException.class)
    protected ResponseEntity<Object> handleEmailExistException(DuplicateMailException ex) {
        return buildResponseEntity(ApiError.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT).build());
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
