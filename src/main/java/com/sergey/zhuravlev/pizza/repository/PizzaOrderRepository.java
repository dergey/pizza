package com.sergey.zhuravlev.pizza.repository;

import com.sergey.zhuravlev.pizza.entity.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Long> {
}
