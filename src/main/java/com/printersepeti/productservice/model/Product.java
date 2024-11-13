package com.printersepeti.productservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product implements Serializable {
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "business_id")
    private Long businessID;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price")
    private BigDecimal price;
}
