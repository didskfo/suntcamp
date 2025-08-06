package com.example.suntcamp.service;

import com.example.suntcamp.domain.Product;
import com.example.suntcamp.dto.ProductRequestDto;
import com.example.suntcamp.dto.CategoryDto;
import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ProductSearchCriteria;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.repository.ProductRepository;
import com.example.suntcamp.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록
    public Product createProduct(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setPhotoUrl(dto.getPhotoUrl());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        return productRepository.save(product);
    }

    // 상품 수정
    public Product updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setPhotoUrl(dto.getPhotoUrl());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());

        return productRepository.save(product);
    }

    // 상품 삭제
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 상품이 없습니다. id=" + id);
        }
        productRepository.deleteById(id);
    }

    // 상품 목록 조회 (검색 + 정렬 포함)
    @Transactional(readOnly = true)
    public ResponseDto<List<ProductDto>> getAllProducts(ProductSearchCriteria criteria) {
        Specification<Product> spec = Specification.<Product>unrestricted()
                .and(ProductSpecification.hasPriceGreaterThanOrEq(criteria.getMinPrice()))
                .and(ProductSpecification.hasPriceLessThanOrEq(criteria.getMaxPrice()))
                .and(ProductSpecification.hasKeyword(criteria.getKeyword()))
                .and(ProductSpecification.hasCategory(criteria.getCategoryId()));

        Sort sort = Sort.by("createdAt");
        if ("oldest".equalsIgnoreCase(criteria.getSortBy())) {
            sort = sort.ascending();
        } else {
            sort = sort.descending(); // default to newest
        }

        List<Product> products = productRepository.findAll(spec, sort);

        List<ProductDto> productDtos = products.stream()
                .map(p -> ProductDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .stock(p.getStock())
                        .photoUrl(p.getPhotoUrl())
                        .description(p.getDescription())
                        .category(CategoryDto.builder()
                                .id(p.getCategory().getId())
                                .name(p.getCategory().getName())
                                .build())
                        .build()
                ).toList();

        return ResponseDto.success(productDtos);
    }
}
