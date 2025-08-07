package com.example.suntcamp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    private String description;

    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void updateInfo(String name,
                           Integer price,
                           Integer stock,
                           String photoUrl,
                           String description,
                           Category category) {
        if (name != null)        this.name        = name;
        if (price != null)       this.price       = price;
        if (stock != null)       this.stock       = stock;
        if (photoUrl != null)    this.photoUrl    = photoUrl;
        if (description != null) this.description = description;
        if (category != null)    this.category    = category;
    }
}
