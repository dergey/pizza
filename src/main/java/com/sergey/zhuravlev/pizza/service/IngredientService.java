package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.Ingredient;
import com.sergey.zhuravlev.pizza.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public Page<Ingredient> list(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Ingredient getIngredient(Long id) {
        return ingredientRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Ingredient createIngredient(String title, Long shelfLife) {
        Ingredient ingredient = new Ingredient(null, title, shelfLife);
        return ingredientRepository.save(ingredient);
    }

    @Transactional
    public Ingredient updateIngredient(Long id, String title, Long shelfLife) {
        Ingredient ingredient = ingredientRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
        ingredient.setTitle(title);
        ingredient.setShelfLife(shelfLife);
        return ingredientRepository.save(ingredient);
    }

    @Transactional
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

}
