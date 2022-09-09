package com.netz00.hibernatesearch6example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

        List<String> details = new ArrayList<>();
        details.add("Resource Not Found");
        details.add("TIME: " + String.valueOf(LocalDateTime.now()));
        details.add("MESSAGE: " + ex.getMessage());

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }


}
