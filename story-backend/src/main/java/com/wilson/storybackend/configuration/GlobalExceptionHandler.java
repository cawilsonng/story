package com.wilson.storybackend.configuration;

import com.wilson.storybackend.exceptions.ParameterException;
import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.models.CommonResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log
public class GlobalExceptionHandler {
    @ExceptionHandler(TargetNotFoundException.class)
    public ResponseEntity<CommonResponse> handleTargetNotFoundException() {
        CommonResponse response = CommonResponse.builder().code(404).message("target not existed.").build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<CommonResponse> handleParameterException() {
        CommonResponse response = CommonResponse.builder().code(400).message("invalid client parameter").build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception e) {
        log.severe(e.getMessage());
        CommonResponse response = CommonResponse.builder().code(500).message("error occurred.").build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
