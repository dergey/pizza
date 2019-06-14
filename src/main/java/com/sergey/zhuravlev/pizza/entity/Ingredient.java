package com.sergey.zhuravlev.pizza.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredients", uniqueConstraints = @UniqueConstraint(columnNames = "ingredient_title", name = "uk_ingredient_title"))
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "ingredient_title", length = 100, nullable = false)
    private String title;

    @Column(name = "shelf_life", nullable = false)
    private Long shelfLife;

}
