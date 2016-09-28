package com.github.aleksandermielczarek.realmrepository.processor.exception;

/**
 * Created by Aleksander Mielczarek on 28.09.2016.
 */

public class RealmRepositoryException extends RuntimeException {

    private final String message;

    public RealmRepositoryException(String message) {
        super(message);
        this.message = message;
    }

    public RealmRepositoryException(String message, Throwable cause) {
        super(message, cause);
        this.message = message + " " + cause.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
