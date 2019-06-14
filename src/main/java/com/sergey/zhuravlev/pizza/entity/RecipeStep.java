package com.sergey.zhuravlev.pizza.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe_step")
public class RecipeStep {

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class RecipeStepId implements Serializable {

        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recipe_step_recipe"))
        private Recipe recipe;

        @Column(name = "step", nullable = false)
        private Integer step;

    }

    @EmbeddedId
    private RecipeStepId id;

    @Column(name = "recipe_step_description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "ingredient_recipe_steps",
            joinColumns = {
                    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id", foreignKey = @ForeignKey(name = "fk_ingredient_recipe_steps_recipe")),
                    @JoinColumn(name = "recipe_step", referencedColumnName = "step", foreignKey = @ForeignKey(name = "fk_ingredient_recipe_steps_recipe_step"))},
            inverseJoinColumns = {
                    @JoinColumn(name = "ingredient_id", referencedColumnName = "ingredient_id", foreignKey = @ForeignKey(name = "fk_ingredient_recipe_steps_ingredient"))
            })
    private Collection<Ingredient> ingredients;

}
