package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.Ingredient;
import com.sergey.zhuravlev.pizza.entity.StockIngredient;
import com.sergey.zhuravlev.pizza.enums.StockType;
import com.sergey.zhuravlev.pizza.repository.IngredientRepository;
import com.sergey.zhuravlev.pizza.repository.StockIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class StockIngredientService {

    private final IngredientRepository ingredientRepository;
    private final StockIngredientRepository stockIngredientRepository;

    @Transactional(readOnly = true)
    public Page<StockIngredient> list(Pageable pageable) {
        return stockIngredientRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public StockIngredient getStockIngredient(Long id) {
        return stockIngredientRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public StockIngredient createStockIngredient(Long ingredientId,
                                                 Date deliveryDate,
                                                 StockType stockType,
                                                 Long count,
                                                 BigDecimal weight) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(EntityNotFoundException::new);
        StockIngredient stockIngredient = new StockIngredient(
                null, ingredient, deliveryDate, stockType, count, weight);
        return stockIngredientRepository.save(stockIngredient);
    }

    @Transactional
    public StockIngredient updateStockIngredient(Long id,
                                                 Long ingredientId,
                                                 Date deliveryDate,
                                                 StockType stockType,
                                                 Long count,
                                                 BigDecimal weight) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(EntityNotFoundException::new);
        StockIngredient stockIngredient = stockIngredientRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        stockIngredient.setIngredient(ingredient);
        stockIngredient.setDeliveryDate(deliveryDate);
        stockIngredient.setStockType(stockType);
        stockIngredient.setCount(count);
        stockIngredient.setWeight(weight);
        return stockIngredientRepository.save(stockIngredient);
    }

    @Transactional
    public void deleteStockIngredient(Long id) {
        stockIngredientRepository.deleteById(id);
    }


}
