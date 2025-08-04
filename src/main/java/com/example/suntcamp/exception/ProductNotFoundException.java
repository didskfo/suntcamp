package com.example.suntcamp.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Product Not Found: " + productId);
    }
}
