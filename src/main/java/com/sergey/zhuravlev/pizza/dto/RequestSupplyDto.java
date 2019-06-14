package com.sergey.zhuravlev.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSupplyDto {

    @NotNull
    private Long ingredientId;

    @NotNull
    private Long supplyTime;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String currency;

}
