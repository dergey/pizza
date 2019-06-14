package com.sergey.zhuravlev.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sergey.zhuravlev.pizza.enums.BoardThickness;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequestDto {

    @JsonProperty("pizza")
    private PizzaRequestDto pizza;

    @JsonProperty("board_thickness")
    private BoardThickness boardThickness;

    @JsonProperty("cook_time")
    private Long cookTime;

    @JsonProperty("steps")
    private Collection<RecipeStepRequestDto> steps;

}
