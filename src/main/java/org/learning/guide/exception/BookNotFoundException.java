package org.learning.guide.exception;

public class BookNotFoundException extends RuntimeException {

    private final Long bookId;

    public BookNotFoundException(Long bookId, String message) {
        super(message);
        this.bookId = bookId;
    }
}
