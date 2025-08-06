package com.example.suntcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private String status;
    private T data;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>("success", data);
    }

    public static ResponseDto<String> error(String errorMessage) {
        return new ResponseDto<>("error", errorMessage);
    }
}