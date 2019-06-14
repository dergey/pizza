package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.Pizza;
import com.sergey.zhuravlev.pizza.repository.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    @Transactional(readOnly = true)
    public Page<Pizza> list(Pageable pageable) {
        return pizzaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Pizza getPizza(Long id) {
        return pizzaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Pizza createPizza(String title,
                             BigDecimal price,
                             String currency,
                             BigDecimal fat,
                             BigDecimal protein,
                             BigDecimal carbohydrate) {
        Pizza pizza = new Pizza(null, title, price, currency, fat, protein, carbohydrate);
        return pizzaRepository.save(pizza);
    }

    @Transactional
    public Pizza updatePizza(Long id,
                             String title,
                             BigDecimal price,
                             String currency,
                             BigDecimal fat,
                             BigDecimal protein,
                             BigDecimal carbohydrate) {
        Pizza pizza = pizzaRepository.getOne(id);
        pizza.setTitle(title);
        pizza.setPrice(price);
        pizza.setCurrency(currency);
        pizza.setFat(fat);
        pizza.setProtein(protein);
        pizza.setCarbohydrate(carbohydrate);
        return pizzaRepository.save(pizza);
    }

    @Transactional
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }

}
