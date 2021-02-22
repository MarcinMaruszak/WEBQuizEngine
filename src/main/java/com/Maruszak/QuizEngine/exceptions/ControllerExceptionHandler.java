package com.Maruszak.QuizEngine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler (IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Quiz not found")
    public void IndexOutOfBoundsExceptionHandler(){}

}
