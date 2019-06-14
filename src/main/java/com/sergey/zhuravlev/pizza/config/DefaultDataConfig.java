package com.sergey.zhuravlev.pizza.config;

import com.sergey.zhuravlev.pizza.entity.User;
import com.sergey.zhuravlev.pizza.enums.UserRole;
import com.sergey.zhuravlev.pizza.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DefaultDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        userRepository.save(new User("admin", passwordEncoder.encode("admin"), true, null, new HashSet<>(Arrays.asList(UserRole.ROLE_ADMIN, UserRole.ROLE_USER))));
        userRepository.save(new User("user1", passwordEncoder.encode("12345678"), true, null, new HashSet<>(Collections.singletonList(UserRole.ROLE_USER))));
        userRepository.save(new User("user2", passwordEncoder.encode("12345678"), true, null, new HashSet<>(Collections.singletonList(UserRole.ROLE_USER))));
    }
}
