package ru.erik182.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.erik182.models.Exam;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamRowMapper implements RowMapper<Exam> {
    @Override
    public Exam mapRow(ResultSet resultSet, int i) throws SQLException {
        return Exam.builder()
                .id(resultSet.getLong("exam_id"))
                .subject(resultSet.getString("subject"))
                .score(resultSet.getInt("score"))
                .build();
    }
}
