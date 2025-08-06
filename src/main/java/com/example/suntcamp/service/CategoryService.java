package com.example.suntcamp.service;

import com.example.suntcamp.domain.Category;
import com.example.suntcamp.dto.CategoryDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseDto<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> dtos = categoryRepository.findAll().stream()
                .map(c -> new CategoryDto(c.getId(), c.getName()))
                .toList();
        return ResponseDto.success(dtos);
    }

    @Transactional
    public ResponseDto<CategoryDto> createCategory(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(), categoryDto.getName());
        categoryRepository.save(category);
        return ResponseDto.success(new CategoryDto(category.getId(), category.getName()));
    }
}
