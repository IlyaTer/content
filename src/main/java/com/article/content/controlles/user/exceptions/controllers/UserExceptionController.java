package com.article.content.controlles.user.exceptions.controllers;

import com.article.content.controlles.user.exceptions.others.NoSuchUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class UserExceptionController {
    
    @ExceptionHandler(value = NoSuchUserException.class)
    public ResponseEntity<Object> exception(NoSuchUserException exception) {
        return new ResponseEntity<>("No Such User", HttpStatus.NOT_FOUND);
    }
}
