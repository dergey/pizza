package com.sergey.zhuravlev.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeStepRequestDto {

    @JsonProperty("step")
    private Integer step;

    @JsonProperty("description")
    private String description;

    @JsonProperty("ingredient_ids")
    private Collection<Long> ingredientIds;

}
