package com.sergey.zhuravlev.pizza.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
@RequiredArgsConstructor
public class TableDtoExtractor implements ResultSetExtractor<TableDto> {

    @Override
    public TableDto extractData(ResultSet rs) throws SQLException {
        List<String> columns = new ArrayList<>();
        List<TableRow> results = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            columns.add(JdbcUtils.lookupColumnName(rsmd, i));
        }

        int rowNum = 0;
        while (rs.next()) {
            results.add(this.mapRow(rs, rowNum++, rsmd.getColumnCount()));
        }
        return new TableDto(columns, results);
    }

    public TableRow mapRow(ResultSet rs, int rowNum, int columnCount) throws SQLException {
        List<Object> row = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            row.add(JdbcUtils.getResultSetValue(rs, i));
        }
        return new TableRow(rowNum, row);
    }

}
