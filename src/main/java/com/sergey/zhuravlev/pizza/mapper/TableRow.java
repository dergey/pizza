package com.sergey.zhuravlev.pizza.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TableRow {

    private final int index;
    private final List<Object> items;

}
