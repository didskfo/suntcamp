package com.example.suntcamp.controller;

import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ProductRequestDto;
import com.example.suntcamp.dto.ProductSearchCriteria;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Product-Controller", description = "상품-관련-API")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "상품 생성하기")
    public ResponseDto<ProductDto> createProduct(@RequestBody ProductRequestDto request) {
        return ResponseDto.success(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "상품 조회하기")
    public ResponseDto<ProductDto> getProductsById(@RequestParam Long id) {
        return ResponseDto.success(productService.getProduct(id));
    }

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
    @Operation(summary = "상품 수정하기")
    public ResponseDto<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto request) {
        return ResponseDto.success(productService.updateProduct(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "상품 삭제하기")
    public ResponseDto<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseDto.success("Product is deleted successfully");
    }
}
