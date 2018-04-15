package com.landvibe.hackday.batch;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

class JdbcReader {
    static <T> JdbcCursorItemReader<T> jdbcReader(Class<T> classed, DataSource dataSource, String sql) {
        return new JdbcCursorItemReaderBuilder<T>()
                .dataSource(dataSource)
                .sql(sql)
                .saveState(false)
                .rowMapper(new BeanPropertyRowMapper<T>(classed))
                .build();

    }
}
