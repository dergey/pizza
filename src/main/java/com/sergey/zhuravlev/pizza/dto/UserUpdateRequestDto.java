package com.sergey.zhuravlev.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sergey.zhuravlev.pizza.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {

    @JsonProperty("role")
    private Set<UserRole> role;

}
