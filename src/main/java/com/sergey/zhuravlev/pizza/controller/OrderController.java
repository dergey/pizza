package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.dto.order.OrderCreateRequestDto;
import com.sergey.zhuravlev.pizza.dto.order.OrderUpdateRequestDto;
import com.sergey.zhuravlev.pizza.dto.order.PizzaOrderUpdateRequestDto;
import com.sergey.zhuravlev.pizza.dto.pageable.PageDto;
import com.sergey.zhuravlev.pizza.dto.supplier.SupplierCreateRequestDto;
import com.sergey.zhuravlev.pizza.dto.supplier.SupplierUpdateRequestDto;
import com.sergey.zhuravlev.pizza.entity.*;
import com.sergey.zhuravlev.pizza.service.CustomerService;
import com.sergey.zhuravlev.pizza.service.OrderService;
import com.sergey.zhuravlev.pizza.service.PizzaOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PizzaOrderService pizzaOrderService;

    private final CustomerService customerService;

    @GetMapping(path = "/orders")
    public PageDto<Order> list(@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return new PageDto<>(orderService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.getOrder(id));
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderCreateRequestDto orderCreateRequestDto)
            throws URISyntaxException {
        Customer customer = customerService.getCustomer(orderCreateRequestDto.getCustomerId());
        Order order = orderService.createOrder(customer,
                orderCreateRequestDto.getOrderDate(),
                orderCreateRequestDto.getTotalPrice(),
                orderCreateRequestDto.getCurrency());
        Collection<PizzaOrder> pizzaOrders = pizzaOrderService.createPizzaOrders(order, orderCreateRequestDto.getOrders());
        orderService.updatePizzaOrders(order.getId(), pizzaOrders);
        return ResponseEntity.created(new URI("/api/stock/" + order.getId())).body(order);
    }

    @PutMapping(value = "/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id,
                                                 @RequestBody @Valid OrderUpdateRequestDto orderUpdateRequestDto) {
        Customer customer = customerService.getCustomer(orderUpdateRequestDto.getCustomerId());
        Order order = orderService.getOrder(id);
        Collection<PizzaOrder> pizzaOrders = pizzaOrderService.updatePizzaOrders(order, orderUpdateRequestDto.getPizzaOrders());
        order = orderService.updateOrder(
                id,
                pizzaOrders,
                customer,
                orderUpdateRequestDto.getOrderDate(),
                orderUpdateRequestDto.getTotalPrice(),
                orderUpdateRequestDto.getCurrency()
        );
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping(value = "/orders/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
