package com.sergey.zhuravlev.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRequestDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("shelfLife")
    private Long shelfLife;

}
