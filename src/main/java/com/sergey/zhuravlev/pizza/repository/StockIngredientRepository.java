package com.sergey.zhuravlev.pizza.repository;

import com.sergey.zhuravlev.pizza.entity.StockIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockIngredientRepository extends JpaRepository<StockIngredient, Long> {
}

