package com.sergey.zhuravlev.pizza.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pizzas", uniqueConstraints = @UniqueConstraint(columnNames = "pizza_title", name = "uk_pizza_title"))
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pizza_id")
    private Long id;

    @Column(name = "pizza_title", length = 100, nullable = false)
    private String title;

    @Column(name = "pizza_price", precision = 14, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "pizza_price_currency", length = 3, nullable = false)
    private String currency;

    @Column(name = "fat", nullable = false, precision = 10, scale = 2)
    private BigDecimal fat;

    @Column(name = "protein", nullable = false, precision = 10, scale = 2)
    private BigDecimal protein;

    @Column(name = "carbohydrate", nullable = false, precision = 10, scale = 2)
    private BigDecimal carbohydrate;

}
