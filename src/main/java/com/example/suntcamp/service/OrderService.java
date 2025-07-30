package com.example.suntcamp.service;

import com.example.suntcamp.domain.Order;
import com.example.suntcamp.domain.OrderItem;
import com.example.suntcamp.domain.Product;
import com.example.suntcamp.dto.OrderRequestDto;
import com.example.suntcamp.dto.OrderResponseDto;
import com.example.suntcamp.dto.ProductDto;
import com.example.suntcamp.dto.ResponseDto;
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
        List<OrderItem> savedItems = orderRequest.getItems().stream().map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    if (product.getStock() < item.getQuantity()) {
                        throw new RuntimeException("Stock exceeds quantity");
                    }

                    product.setStock(product.getStock() - item.getQuantity());
                    productRepository.save(product);

                    OrderItem orderItem = new OrderItem(product, item.getQuantity());
                    order.addItem(orderItem);
                    return orderItem;
                })
                .toList();

        orderRepository.save(order);

        List<ProductDto> orderedProducts = savedItems.stream()
                .map(orderItem -> ProductDto.builder()
                        .id(orderItem.getProduct().getId())
                        .name(orderItem.getProduct().getName())
                        .price(orderItem.getPrice())
                        .stock(orderItem.getProduct().getStock())
                        .photoUrl(orderItem.getProduct().getPhotoUrl())
                        .build()
                )
                .toList();

        OrderResponseDto responseDto = OrderResponseDto.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .products(orderedProducts)
                .build();

        return ResponseDto.success(responseDto);
    }
}
