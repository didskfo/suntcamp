package com.example.suntcamp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private String name;
    private int price;
    private int stock;
}
