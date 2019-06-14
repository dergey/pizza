package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.dto.order.PizzaOrderRequestDto;
import com.sergey.zhuravlev.pizza.dto.order.PizzaOrderUpdateRequestDto;
import com.sergey.zhuravlev.pizza.dto.supplier.SupplyRequestDto;
import com.sergey.zhuravlev.pizza.dto.supplier.SupplyUpdateRequestDto;
import com.sergey.zhuravlev.pizza.entity.*;
import com.sergey.zhuravlev.pizza.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PizzaOrderService {

    private final PizzaOrderRepository pizzaOrderRepository;
    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;

    @Transactional
    public PizzaOrder createPizzaOrder(Order order, PizzaOrderRequestDto pizzaOrderRequestDto) {
        Pizza pizza = pizzaRepository
                .findById(pizzaOrderRequestDto.getPizzaId())
                .orElseThrow(EntityNotFoundException::new);
        PizzaOrder pizzaOrder = new PizzaOrder(null,
                order,
                pizza,
                pizzaOrderRequestDto.getCount());
        return pizzaOrderRepository.save(pizzaOrder);
    }

    @Transactional
    public Collection<PizzaOrder> createPizzaOrders(Order order, Collection<PizzaOrderRequestDto> pizzaOrdersDto) {
        Collection<PizzaOrder> result = new ArrayList<>();
        for (PizzaOrderRequestDto pizzaOrderRequestDto : pizzaOrdersDto) {
            result.add(createPizzaOrder(order, pizzaOrderRequestDto));
        }
        return result;
    }

    @Transactional
    public Collection<PizzaOrder> updatePizzaOrders(Order order, Collection<PizzaOrderUpdateRequestDto> pizzaOrderDtos) {
        Collection<PizzaOrder> result = new ArrayList<>();
        order = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        Map<Long, PizzaOrder> oldPizzaOrder = order.getOrders().stream().collect(Collectors.toMap(PizzaOrder::getId, s -> s));
        for (PizzaOrderUpdateRequestDto pizzaOrderDto : pizzaOrderDtos) {
            Pizza pizza = pizzaRepository.findById(pizzaOrderDto.getPizzaId())
                    .orElseThrow(EntityNotFoundException::new);
            PizzaOrder pizzaOrder;
            if (pizzaOrderDto.getId() != null) {
                pizzaOrder = oldPizzaOrder.remove(pizzaOrderDto.getId());
                if (pizzaOrder == null) {
                    throw new EntityNotFoundException();
                }
                pizzaOrder.setOrder(order);
                pizzaOrder.setPizza(pizza);
                pizzaOrder.setCount(pizzaOrderDto.getCount());
            } else {
                pizzaOrder = new PizzaOrder(null,
                        order,
                        pizza,
                        pizzaOrderDto.getCount());
            }
            pizzaOrder = pizzaOrderRepository.save(pizzaOrder);
            result.add(pizzaOrder);
        }

        for (PizzaOrder value : oldPizzaOrder.values()) {
            order.getOrders().remove(value);
            pizzaOrderRepository.deleteById(value.getId());
        }

        return result;
    }

}
