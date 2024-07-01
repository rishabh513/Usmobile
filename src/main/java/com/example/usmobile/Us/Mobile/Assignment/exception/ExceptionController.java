package com.example.usmobile.Us.Mobile.Assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionController {
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<String> notFoundExceptionResponseEntity(CustomException e){

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
