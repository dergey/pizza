package com.sergey.zhuravlev.pizza.converter;

import com.sergey.zhuravlev.pizza.dto.login.UserDto;
import com.sergey.zhuravlev.pizza.entity.User;

import java.util.stream.Collectors;

public class UserConverter {

    public static UserDto getUserDto(User user) {
        return new UserDto(user.getUsername(),
                user.getAuthorities().stream().map(Enum::name).collect(Collectors.toList()));
    }

}
