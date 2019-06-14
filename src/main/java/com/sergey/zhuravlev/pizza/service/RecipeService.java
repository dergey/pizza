package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.Pizza;
import com.sergey.zhuravlev.pizza.entity.Recipe;
import com.sergey.zhuravlev.pizza.entity.RecipeStep;
import com.sergey.zhuravlev.pizza.enums.BoardThickness;
import com.sergey.zhuravlev.pizza.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Transactional(readOnly = true)
    public Recipe getRecipe(Long id) {
        return recipeRepository.getOne(id);
    }

    @Transactional
    public Recipe createRecipe(Pizza pizza, BoardThickness boardThickness, Long cookTime, Collection<RecipeStep> steps) {
        Recipe recipe = new Recipe(null, pizza, boardThickness, cookTime, steps);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Long id, Pizza pizza, BoardThickness boardThickness, Long cookTime, Collection<RecipeStep> steps) {
        Recipe recipe = recipeRepository.getOne(id);
        recipe.setPizza(pizza);
        recipe.setBoardThickness(boardThickness);
        recipe.setCookTime(cookTime);
        recipe.setSteps(steps);
        return recipe;
    }

    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

}
