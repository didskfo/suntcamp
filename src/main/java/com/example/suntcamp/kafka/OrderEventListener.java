package com.example.suntcamp.kafka;

import com.example.suntcamp.domain.Order;
import com.example.suntcamp.domain.OrderItem;
import com.example.suntcamp.domain.Product;
import com.example.suntcamp.dto.OrderItemRequestDto;
import com.example.suntcamp.repository.OrderRepository;
import com.example.suntcamp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "orderCreate", groupId = "suntcamp-group")
    @Transactional
    public void handleOrderCreated(OrderCreateEvent event) {
        log.info("Processing OrderCreateEvent: {} items", event.getItems().size());

        Order order = new Order();
        for (OrderItemRequestDto itemDto : event.getItems()) {
            Product product = productRepository.findByIdForUpdate(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < itemDto.getQuantity()) {
                throw new RuntimeException("Not enough stock");
            }
            product.setStock(product.getStock() - itemDto.getQuantity());

            OrderItem orderItem = new OrderItem(product, itemDto.getQuantity());
            order.addItem(orderItem);
        }
        orderRepository.save(order);
        log.info("Order {} saved with {} items", order.getId(), order.getItems().size());
    }
}
