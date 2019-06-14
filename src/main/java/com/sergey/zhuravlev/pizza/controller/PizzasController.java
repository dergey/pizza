package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.dto.PizzaRequestDto;
import com.sergey.zhuravlev.pizza.dto.pageable.PageDto;
import com.sergey.zhuravlev.pizza.entity.Pizza;
import com.sergey.zhuravlev.pizza.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PizzasController {

    private final PizzaService pizzaService;

    @GetMapping(path = "/pizzas")
    public PageDto<Pizza> list(@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return new PageDto<>(pizzaService.list(PageRequest.of(page, size)));
    }

    @GetMapping("/pizzas/{id}")
    public ResponseEntity<Pizza> getPizza(@PathVariable Long id) {
        return ResponseEntity.ok().body(pizzaService.getPizza(id));
    }

    @PostMapping(value = "/pizzas")
    public ResponseEntity<Pizza> createPizza(@RequestBody @Valid PizzaRequestDto pizzaRequestDto) throws URISyntaxException {
        Pizza pizza = pizzaService.createPizza(
                pizzaRequestDto.getTitle(),
                pizzaRequestDto.getPrice(),
                pizzaRequestDto.getCurrency(),
                pizzaRequestDto.getFat(),
                pizzaRequestDto.getProtein(),
                pizzaRequestDto.getCarbohydrate());
        return ResponseEntity.created(new URI("/api/pizzas/" + pizza.getId())).body(pizza);
    }

    @PutMapping(value = "/pizzas/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Long id,
                                             @RequestBody @Valid PizzaRequestDto pizzaRequestDto) {
        Pizza ingredient = pizzaService.updatePizza(
                id,
                pizzaRequestDto.getTitle(),
                pizzaRequestDto.getPrice(),
                pizzaRequestDto.getCurrency(),
                pizzaRequestDto.getFat(),
                pizzaRequestDto.getProtein(),
                pizzaRequestDto.getCarbohydrate());
        return ResponseEntity.ok().body(ingredient);
    }

    @DeleteMapping(value = "/pizzas/{id}")
    public ResponseEntity deleteIngredient(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }

}
