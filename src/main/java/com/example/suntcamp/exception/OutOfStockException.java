package com.example.suntcamp.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String productName) {
        super("Insufficient Stock For Product: " + productName);
    }
}
