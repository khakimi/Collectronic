package com.example.collectronic.exceptions;

public class UserCollectionNotFoundException extends RuntimeException {
    public UserCollectionNotFoundException(String message) {
        super(message);
    }
}
