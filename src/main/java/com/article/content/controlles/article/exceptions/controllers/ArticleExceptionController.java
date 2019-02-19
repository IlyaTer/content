package com.article.content.controlles.article.exceptions.controllers;

import com.article.content.controlles.article.exceptions.classes.general.ArticleException;
import com.article.content.controlles.article.exceptions.classes.others.ArticleNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ArticleExceptionController {
    
   @ExceptionHandler(value = ArticleNotfoundException.class)
   public ResponseEntity<Object> exception(ArticleNotfoundException exception) {
      return new ResponseEntity<>("Article not found", HttpStatus.NOT_FOUND);
   }
}
