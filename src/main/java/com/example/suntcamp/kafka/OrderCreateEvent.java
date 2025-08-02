package com.example.suntcamp.kafka;

import com.example.suntcamp.dto.OrderItemRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateEvent {

    private List<OrderItemRequestDto> items;
}
