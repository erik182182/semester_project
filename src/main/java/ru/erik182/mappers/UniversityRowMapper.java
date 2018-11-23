package ru.erik182.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.erik182.models.University;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversityRowMapper implements RowMapper<University> {
    @Override
    public University mapRow(ResultSet resultSet, int i) throws SQLException {
        return University.builder()
                .id(resultSet.getLong("id_uni"))
                .name(resultSet.getString("uni_name"))
                .city(resultSet.getString("city"))
                .info(resultSet.getString("uni_info"))
                .build();
    }
}
