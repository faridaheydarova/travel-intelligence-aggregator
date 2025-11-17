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

    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<ErrorResponse> ex (WeatherServiceException ex){
        ErrorResponse body=new ErrorResponse("WEATHER_ERROR", ex.getMessage());

        return ResponseEntity
                .status(502)
                .body(body);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> ex (Exception ex){
        ErrorResponse body=new ErrorResponse("INTERNAL_ERROR", ex.getMessage());

        return ResponseEntity
                .status(500)
                .body(body);
    }
}
