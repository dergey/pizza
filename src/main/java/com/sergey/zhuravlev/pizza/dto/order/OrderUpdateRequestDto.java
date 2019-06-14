package com.sergey.zhuravlev.pizza.dto.order;

import com.sergey.zhuravlev.pizza.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequestDto {

    private Collection<PizzaOrderUpdateRequestDto> pizzaOrders;

    private Long customerId;

    private Date orderDate;

    private BigDecimal totalPrice;

    private String currency;

}
