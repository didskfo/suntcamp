package com.example.suntcamp.controller;

import com.example.suntcamp.dto.CategoryDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category-Controller", description = "카테고리-관련-API")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "카테고리 목록 조회")
    public ResponseDto<List<CategoryDto>> list() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    @Operation(summary = "카테고리 생성")
    public ResponseDto<CategoryDto> create(@RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }
}
