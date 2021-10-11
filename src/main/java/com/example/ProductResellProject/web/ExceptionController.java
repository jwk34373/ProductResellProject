package com.example.ProductResellProject.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleIllegalStateException(RuntimeException err){
        log.warn("handleIllegalStateException", err);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
    }

}
