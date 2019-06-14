package com.sergey.zhuravlev.pizza.repository;

import com.sergey.zhuravlev.pizza.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
