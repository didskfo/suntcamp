package com.example.suntcamp.exception;

public class InvalidCategoryException extends RuntimeException {
    public InvalidCategoryException(String name) {
        super("Invalid category: " + name);
    }
}
