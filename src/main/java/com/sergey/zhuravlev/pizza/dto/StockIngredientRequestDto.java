package com.sergey.zhuravlev.pizza.dto;

import com.sergey.zhuravlev.pizza.enums.StockType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockIngredientRequestDto {

    @NotNull
    private Long ingredientId;

    @NotNull
    private Date deliveryDate;

    @NotNull
    private StockType stockType;

    private Long count;

    private BigDecimal weight;

}
