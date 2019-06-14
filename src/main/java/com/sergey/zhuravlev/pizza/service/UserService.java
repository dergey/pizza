package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.entity.User;
import com.sergey.zhuravlev.pizza.enums.UserRole;
import com.sergey.zhuravlev.pizza.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public User createUser(String name, String password, Set<UserRole> role) {
        User user = new User(name, passwordEncoder.encode(password), true, null, role);
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(String name, Set<UserRole> role) {
        User user = userRepository.findByUsername(name).orElseThrow(EntityNotFoundException::new);
        user.setAuthorities(role);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String name) {
        userRepository.deleteByUsername(name);
    }

}
