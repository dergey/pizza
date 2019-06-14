package com.sergey.zhuravlev.pizza.service;

import com.sergey.zhuravlev.pizza.mapper.TableDto;
import com.sergey.zhuravlev.pizza.mapper.TableDtoExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLSyntaxErrorException;

@Service
@RequiredArgsConstructor
public class SqlService {

    private final JdbcTemplate jdbcTemplate;

    enum QueryType {
        READ,
        WRITE
    }

    public TableDto executeSql(String sql) throws SQLSyntaxErrorException {
        if (parseType(sql) == QueryType.WRITE) {
            jdbcTemplate.update(sql);
            return TableDto.TABLE_OK;
        } else {
            return jdbcTemplate.query(sql, new TableDtoExtractor());
        }
    }

    QueryType parseType(String sql) throws SQLSyntaxErrorException {
        if (sql.toUpperCase().startsWith("UPDATE")) {
            return QueryType.WRITE;
        } else if (sql.toUpperCase().startsWith("DELETE")) {
            return QueryType.WRITE;
        } else if (sql.toUpperCase().startsWith("INSERT")) {
            return QueryType.WRITE;
        } else if (sql.toUpperCase().startsWith("CREATE")) {
            return QueryType.WRITE;
        } else if (sql.toUpperCase().startsWith("SELECT")) {
            return QueryType.READ;
        } else {
            throw new SQLSyntaxErrorException("Not support type of request.");
        }
    }

}
