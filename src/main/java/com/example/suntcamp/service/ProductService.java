package com.example.suntcamp.service;

import com.example.suntcamp.domain.Product;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

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
