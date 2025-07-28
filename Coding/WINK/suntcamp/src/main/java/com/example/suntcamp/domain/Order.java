package com.example.suntcamp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();
}
