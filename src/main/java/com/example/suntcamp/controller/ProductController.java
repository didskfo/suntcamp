package com.example.suntcamp.controller;

import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ProductSearchCriteria;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Product-Controller", description = "상품-관련-API")
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
    @GetMapping
    @Operation(summary = "상품 목록 조회하기")
    public ResponseDto<List<ProductDto>> findAll(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "newest") String sortBy
    ) {
        ProductSearchCriteria criteria = ProductSearchCriteria.builder()
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .keyword(keyword)
                .categoryId(categoryId)
                .sortBy(sortBy)
                .build();

        return productService.getAllProducts(criteria);
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
