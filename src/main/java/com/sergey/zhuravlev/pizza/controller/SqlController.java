package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.dto.sql.SqlRequestDto;
import com.sergey.zhuravlev.pizza.mapper.TableDto;
import com.sergey.zhuravlev.pizza.service.SqlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SqlController {

    private final SqlService sqlService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/sql")
    public ResponseEntity<TableDto> executeSql(@RequestBody @Valid SqlRequestDto sqlRequestDto) throws Exception {
        return ResponseEntity.ok().body(sqlService.executeSql(sqlRequestDto.getSql()));
    }

}
