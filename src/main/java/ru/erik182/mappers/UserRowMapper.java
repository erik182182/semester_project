package ru.erik182.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.erik182.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("user_id"))
                .fullName(resultSet.getString("full_name"))
                .passport(resultSet.getString("passport"))
                .hashPassword(resultSet.getString("password"))
                .type(resultSet.getString("type"))
                .build();
    }
}
