package com.example.suntcamp.controller;

import com.example.suntcamp.dto.OrderRequestDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Order-Controller", description = "주문-관련-API")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문하기")
    public ResponseDto<String> order(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }
}
