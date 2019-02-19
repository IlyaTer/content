package com.article.content.controlles.comment.exceptions.conrollers;

import com.article.content.controlles.comment.exceptions.others.NoSuchCommentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class CommentExceptionController {

    @ExceptionHandler(value = NoSuchCommentException.class)
    public ResponseEntity<Object> exception(NoSuchCommentException exception) {
        return new ResponseEntity<>("No Such Comment", HttpStatus.NOT_FOUND);
    }
}
