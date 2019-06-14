package com.sergey.zhuravlev.pizza.repository;

import com.sergey.zhuravlev.pizza.entity.Order;
import com.sergey.zhuravlev.pizza.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
