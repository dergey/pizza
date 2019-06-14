package com.sergey.zhuravlev.pizza.controller;

import com.sergey.zhuravlev.pizza.dto.error.ErrorDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public final ResponseEntity<ErrorDto> handleException(SQLSyntaxErrorException ex) {
        return ResponseEntity.badRequest().body(new ErrorDto("SQL_CODE_EXCEPTION", ex.getMessage()));
    }


}
