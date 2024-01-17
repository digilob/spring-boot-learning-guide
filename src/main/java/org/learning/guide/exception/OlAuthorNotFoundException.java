package org.learning.guide.exception;

public class OlAuthorNotFoundException extends RuntimeException {

    private final String authorName;

    public OlAuthorNotFoundException(String authorName) {
        this.authorName = authorName;
    }
}
