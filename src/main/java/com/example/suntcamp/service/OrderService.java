package com.example.suntcamp.service;

import com.example.suntcamp.domain.Order;
import com.example.suntcamp.domain.OrderItem;
import com.example.suntcamp.domain.Product;
import com.example.suntcamp.dto.OrderRequestDto;
import com.example.suntcamp.dto.OrderResponseDto;
import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ResponseDto;
import com.example.suntcamp.kafka.OrderCreateEvent;
import com.example.suntcamp.repository.OrderRepository;
import com.example.suntcamp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public ResponseDto<String> createOrder(OrderRequestDto orderRequest) {

        OrderCreateEvent event = new OrderCreateEvent(orderRequest.getItems());
        kafkaTemplate.send("orderCreate", event);

        return ResponseDto.success("Order received and being processed");
    }
}
