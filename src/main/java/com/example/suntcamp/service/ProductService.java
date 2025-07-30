package com.example.suntcamp.service;

import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseDto<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productRepository.findAll().stream()
                .map(p -> ProductDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .stock(p.getStock())
                        .photoUrl(p.getPhotoUrl())
                        .build()
                ).toList();
        return ResponseDto.success(products);
    }
}
