package com.github.badaccuracyid.lacak.challenge1.advice;

import com.github.badaccuracyid.lacak.challenge1.dto.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException ex) {
        String missingParam = ex.getParameterName();
        String errorMessage = String.format(
                "The required parameter '%s' is missing.",
                missingParam
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {
        log.error("Internal server error: ", ex);
        String errorMessage = "An unexpected error occurred.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String errorMessage = "The requested URL is invalid or the HTTP method is not supported.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex) {
        String errorMessage = "The requested URL was not found on the server. Please check your endpoint.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(errorMessage));
    }

}
