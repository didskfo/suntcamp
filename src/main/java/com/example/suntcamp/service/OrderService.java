package com.example.suntcamp.service;

import com.example.suntcamp.domain.Order;
import com.example.suntcamp.domain.OrderItem;
import com.example.suntcamp.domain.Product;
import com.example.suntcamp.dto.*;
import com.example.suntcamp.exception.OutOfStockException;
import com.example.suntcamp.exception.ProductNotFoundException;
import com.example.suntcamp.repository.OrderRepository;
import com.example.suntcamp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ResponseDto<OrderResponseDto> createOrder(OrderRequestDto orderRequest) {
        Order order = new Order();
        List<ProductDto> orderedProducts = orderRequest.getItems().stream().map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(item.getProductId()));

                    if (product.getStock() < item.getQuantity()) {
                        throw new OutOfStockException(product.getName());
                    }

                    product.setStock(product.getStock() - item.getQuantity());

                    OrderItem orderItem = new OrderItem(product, item.getQuantity());
                    order.addItem(orderItem);
                    return ProductDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .stock(product.getStock())
                            .photoUrl(product.getPhotoUrl())
                            .description(product.getDescription())
                            .category(CategoryDto.builder()
                                    .id(product.getCategory().getId())
                                    .name(product.getCategory().getName())
                                    .build())
                            .build();
                })
                .toList();

        orderRepository.save(order);

        OrderResponseDto responseDto = OrderResponseDto.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .products(orderedProducts)
                .build();

        return ResponseDto.success(responseDto);
    }
}
