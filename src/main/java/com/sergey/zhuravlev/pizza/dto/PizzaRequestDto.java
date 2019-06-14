package com.sergey.zhuravlev.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzaRequestDto {

    @NotEmpty
    @JsonProperty("title")
    private String title;

    @NotNull
    @JsonProperty("price")
    private BigDecimal price;

    @NotEmpty
    @Size(min = 3, max = 3)
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("fat")
    private BigDecimal fat;

    @JsonProperty("protein")
    private BigDecimal protein;

    @JsonProperty("carbohydrate")
    private BigDecimal carbohydrate;

}
