package com.sergey.zhuravlev.pizza.repository;

import com.sergey.zhuravlev.pizza.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
