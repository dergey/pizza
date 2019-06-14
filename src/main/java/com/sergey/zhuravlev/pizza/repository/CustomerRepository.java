package com.sergey.zhuravlev.pizza.repository;

import com.sergey.zhuravlev.pizza.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}