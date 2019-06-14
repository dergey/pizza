package com.sergey.zhuravlev.pizza.dto.order;

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
public class PizzaOrderRequestDto {

    @NotNull
    private Long pizzaId;

    @NotNull
    private Long count;

}
