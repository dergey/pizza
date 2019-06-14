package com.sergey.zhuravlev.pizza.repository;

import com.sergey.zhuravlev.pizza.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    void deleteByUsername(String name);
}