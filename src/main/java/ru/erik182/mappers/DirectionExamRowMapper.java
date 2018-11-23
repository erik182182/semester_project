package ru.erik182.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.erik182.models.Exam;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DirectionExamRowMapper implements RowMapper<Exam> {

    @Override
    public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
        return Exam.builder()
                .score(resultSet.getInt("min_score"))
                .subject(resultSet.getString("subject"))
                .build();
    }
}
