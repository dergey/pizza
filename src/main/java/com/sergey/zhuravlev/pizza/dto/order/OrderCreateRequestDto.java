package com.sergey.zhuravlev.pizza.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDto {

    private Long customerId;

    private Date orderDate;

    private Collection<PizzaOrderRequestDto> orders;

    private String currency;

    private BigDecimal totalPrice;

}
