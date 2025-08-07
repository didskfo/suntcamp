package com.example.suntcamp.service;

import com.example.suntcamp.domain.Category;
import com.example.suntcamp.domain.Product;
import com.example.suntcamp.dto.ProductRequestDto;
import com.example.suntcamp.dto.CategoryDto;
import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ProductSearchCriteria;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.exception.InvalidCategoryException;
import com.example.suntcamp.exception.ProductNotFoundException;
import com.example.suntcamp.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductDto createProduct(ProductRequestDto dto) {
        Category category = resolveCategory(dto.getCategoryName());

        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .photoUrl(dto.getPhotoUrl())
                .description(dto.getDescription())
                .category(category)
                .build();

        productRepository.save(product);
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .photoUrl(product.getPhotoUrl())
                .description(product.getDescription())
                .category(new CategoryDto(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                ))
                .build();
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Category category = resolveCategory(dto.getCategoryName());

        product.updateInfo(
                dto.getName(),
                dto.getPrice(),
                dto.getStock(),
                dto.getPhotoUrl(),
                dto.getDescription(),
                category
        );

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .photoUrl(product.getPhotoUrl())
                .description(product.getDescription())
                .category(new CategoryDto(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                ))
                .build();
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

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
            sort = sort.descending();
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

    private Category resolveCategory(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new InvalidCategoryException(categoryName);
        }

        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCat = new Category(categoryName);
                    return categoryRepository.save(newCat);
                });
    }
}
