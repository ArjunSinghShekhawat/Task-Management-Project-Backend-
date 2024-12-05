package com.taskManagement.exceptions;

import com.taskManagement.responce.UserNotFoundResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserNotFoundResponse> userException(UserException userException){
        String message =  userException.getMessage();
        return new ResponseEntity<>(UserNotFoundResponse.builder().message(message).build(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Exception("An error occurred"+ ex.getMessage()));
    }
}
