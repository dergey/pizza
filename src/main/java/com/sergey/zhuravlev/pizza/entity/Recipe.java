package com.sergey.zhuravlev.pizza.entity;

import com.sergey.zhuravlev.pizza.enums.BoardThickness;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pizza_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recipe_pizza"))
    private Pizza pizza;

    @Enumerated(EnumType.STRING)
    @Column(name = "thickness_board", length = 12, nullable = false)
    private BoardThickness boardThickness;

    @Column(name = "cook_time", nullable = false)
    private Long cookTime;

    @OneToMany(targetEntity = RecipeStep.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id.recipe")
    private Collection<RecipeStep> steps;

}
