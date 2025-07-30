package com.example.suntcamp.controller;

import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseDto<List<ProductDto>> findAll() {
        return productService.getAllProducts();
    }
}