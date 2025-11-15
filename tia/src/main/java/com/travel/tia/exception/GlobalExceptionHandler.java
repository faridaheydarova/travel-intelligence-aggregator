package com.travel.tia.exception;

import com.travel.tia.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GeoNotFoundException.class)
    public ResponseEntity<ErrorResponse> ex (GeoNotFoundException ex){
        ErrorResponse body=new ErrorResponse("NOT_FOUND", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }
}
