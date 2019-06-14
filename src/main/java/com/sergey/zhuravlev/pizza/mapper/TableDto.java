package com.sergey.zhuravlev.pizza.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {

    public final static TableDto TABLE_OK = new TableDto(
            Collections.singletonList("RESULT"),
            Collections.singletonList(
                    new TableRow(1, Collections.singletonList("OK"))));

    private List<String> columns;

    private List<TableRow> rows;

}
