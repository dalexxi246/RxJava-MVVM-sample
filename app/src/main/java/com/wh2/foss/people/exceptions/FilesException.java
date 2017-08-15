package com.wh2.foss.people.exceptions;

public class FilesException extends Exception {

    public FilesException(String message) {
        super(message);
    }

    public FilesException(String message, Throwable cause) {
        super(message, cause);
    }
}
