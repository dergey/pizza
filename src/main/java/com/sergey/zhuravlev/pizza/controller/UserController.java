package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.converter.UserConverter;
import com.sergey.zhuravlev.pizza.dto.IngredientRequestDto;
import com.sergey.zhuravlev.pizza.dto.UserRequestDto;
import com.sergey.zhuravlev.pizza.dto.UserUpdateRequestDto;
import com.sergey.zhuravlev.pizza.dto.login.AuthenticateRequestDto;
import com.sergey.zhuravlev.pizza.dto.error.ErrorDto;
import com.sergey.zhuravlev.pizza.dto.login.UserDto;
import com.sergey.zhuravlev.pizza.dto.pageable.PageDto;
import com.sergey.zhuravlev.pizza.entity.Ingredient;
import com.sergey.zhuravlev.pizza.entity.User;
import com.sergey.zhuravlev.pizza.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("home")
    public UserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new UserDto(authentication.getName(), authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    @PostMapping("authenticate")
    public ResponseEntity authenticate(@RequestBody @Valid AuthenticateRequestDto authenticateDto) {
        User user = userService.getUser(authenticateDto.getUsername());

        if (user == null) {
            return new ResponseEntity<>(new ErrorDto("BAD_CREDENTIALS", "Wrong username"),
                    HttpStatus.UNAUTHORIZED);
        }

        boolean valid = passwordEncoder.matches(authenticateDto.getPassword(), user.getPassword());
        if (!valid) {
            return new ResponseEntity<>(new ErrorDto("BAD_CREDENTIALS", "Wrong password"),
                    HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(UserConverter.getUserDto(user));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public PageDto<UserDto> list(@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return new PageDto<>(userService.list(PageRequest.of(page, size)).map(UserConverter::getUserDto));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("{name}")
    public ResponseEntity<UserDto> getIngredient(@PathVariable String name) {
        return ResponseEntity.ok().body(UserConverter.getUserDto(userService.getUser(name)));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) throws URISyntaxException {
        User user = userService.createUser(
                userRequestDto.getName(),
                userRequestDto.getPassword(),
                userRequestDto.getRole());
        return ResponseEntity.created(new URI("/api/users/" + user.getUsername())).body(UserConverter.getUserDto(user));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "{name}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String name,
                                              @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        User user = userService.updateUser(
                name,
                userUpdateRequestDto.getRole());

        return ResponseEntity.ok().body(UserConverter.getUserDto(user));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "{name}")
    public ResponseEntity deleteUser(@PathVariable String name) {
        userService.deleteUser(name);
        return ResponseEntity.noContent().build();
    }

}
