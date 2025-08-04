package com.example.suntcamp.config;

import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.exception.OutOfStockException;
import com.example.suntcamp.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseDto<String>> productNotFound(ProductNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error(e.getMessage()));
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ResponseDto<String>> outOfStock(OutOfStockException e) {
        return ResponseEntity
                .badRequest()
                .body(ResponseDto.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> exception(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(ResponseDto.error("Internal Server Error: " + e.getMessage()));
    }
}
