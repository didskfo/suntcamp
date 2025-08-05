package com.example.suntcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCriteria {
    private Integer maxPrice;
    private Integer minPrice;
    private String keyword;
    private Long categoryId;
    private String sortBy;
}
