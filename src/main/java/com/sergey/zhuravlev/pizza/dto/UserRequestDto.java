package com.sergey.zhuravlev.pizza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sergey.zhuravlev.pizza.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @NotEmpty
    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private Set<UserRole> role;

}
