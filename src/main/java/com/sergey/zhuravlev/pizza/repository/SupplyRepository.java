package com.sergey.zhuravlev.pizza.repository;

import com.sergey.zhuravlev.pizza.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
