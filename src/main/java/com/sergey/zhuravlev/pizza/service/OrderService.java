package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.Customer;
import com.sergey.zhuravlev.pizza.entity.Order;
import com.sergey.zhuravlev.pizza.entity.PizzaOrder;
import com.sergey.zhuravlev.pizza.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<Order> list(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Order getOrder(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Order createOrder(Customer customer, Date orderDate, BigDecimal totalPrice, String currency) {
        Order order = new Order(null, null, customer, orderDate, totalPrice, currency);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updatePizzaOrders(Long orderId, Collection<PizzaOrder> orders) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.setOrders(orders);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id,
                             Collection<PizzaOrder> orders,
                             Customer customer,
                             Date orderDate,
                             BigDecimal totalPrice,
                             String currency) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        order.setOrders(orders);
        order.setCustomer(customer);
        order.setOrderDate(orderDate);
        order.setTotalPrice(totalPrice);
        order.setCurrency(currency);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}