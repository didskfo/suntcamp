package com.example.suntcamp.controller;

import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Product-Controller", description = "상품-관련-API")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "상품 목록 조회하기")
    public ResponseDto<List<ProductDto>> findAll() {
        return productService.getAllProducts();
    }
}