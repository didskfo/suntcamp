package com.example.suntcamp.controller;

import com.example.suntcamp.dto.OrderRequestDto;
import com.example.suntcamp.dto.OrderResponseDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseDto<OrderResponseDto> order(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }
}
