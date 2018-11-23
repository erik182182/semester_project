package ru.erik182.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.erik182.models.Declaration;
import ru.erik182.models.Direction;
import ru.erik182.models.University;
import ru.erik182.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeclarationRowMapper implements RowMapper<Declaration> {
    @Override
    public Declaration mapRow(ResultSet resultSet, int i) throws SQLException {
        return Declaration.builder()
                .direction(Direction.builder().name(resultSet.getString("name"))
                        .university(University.builder().name(resultSet.getString("uni_name")).build())
                        .build())
                .user(User.builder().id(resultSet.getLong("id_user")).build())
                .id(resultSet.getLong("dec_id"))
                .sumScore(resultSet.getInt("sum_score"))
                .build();
    }
}
