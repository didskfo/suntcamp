package com.example.suntcamp.controller;

import com.example.suntcamp.domain.Product;
import com.example.suntcamp.dto.ProductRequestDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 등록
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto request) {
        Product savedProduct = productService.createProduct(request);
        return ResponseEntity.ok(savedProduct);
    }

    // 상품 목록 조회
    @GetMapping("/read")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto<Product>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto request) {
        Product updated = productService.updateProduct(id, request);
        return ResponseEntity.ok(ResponseDto.success(updated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ResponseDto.success(null));
    }


}
