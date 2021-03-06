package com.sergey.zhuravlev.pizza.dto.supplier;

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
public class SupplyUpdateRequestDto {

    @NotNull
    private Long id;
    @NotNull
    private Long ingredientId;
    @NotNull
    private Long supplyTime;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String currency;

}
