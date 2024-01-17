package org.learning.guide.controller;

import org.learning.guide.exception.BookNotFoundException;
import org.learning.guide.exception.OlAuthorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdvice {

    // NOTE: There should really be some kind of a log statement here,
    // as well as a more structured error response, but this is just a demo.

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(NoSuchElementException ex) {
        return "{}";
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBookNotFoundException(BookNotFoundException ex) {
        return "{}";
    }

    public String handleOlAuthorNotFound(OlAuthorNotFoundException ex) {
        return "{}";
    }
}
