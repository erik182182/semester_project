package ru.erik182.repositories;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.erik182.mappers.ExamRowMapper;
import ru.erik182.models.Exam;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ExamRepositoryJdbcTemplateImpl implements ExamRepository {


    private JdbcTemplate jdbcTemplate;

    private final static String SQL_GET_SUBJ_BY_ID = "SELECT * FROM subject WHERE id_subj=?;";

    private final static String SQL_GET_SUBJ_BY_SUBJ = "SELECT * FROM subject WHERE subject=?;";

    private final static String SQL_GET_EXAM = "SELECT * FROM exam WHERE exam_id = ?;";

    private final static String SQL_SAVE_EXAM = "INSERT INTO exam(id_user, id_subj, score)\n" +
            "    VALUES (?,?);";

    private final static String SQL_DELETE_EXAM = "DELETE FROM university WHERE id_uni = ?;";

    private final static String SQL_UPDATE_EXAM = "UPDATE exam SET id_user = ?, score = ? WHERE exam_id = ?;";

    private final static String SQL_GET_ALL_EXAMS = "SELECT * FROM exam;";
    public ExamRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @SneakyThrows
    public Optional<Exam> findOne(Long id) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_GET_EXAM, new ExamRowMapper(), id) );
//        PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQL_GET_EXAM);
//        preparedStatement.setLong(1, id);
//        preparedStatement.execute();
//        ResultSet resultSet = preparedStatement.getResultSet();
//        return Optional.of(new ExamRowMapper().mapRow(resultSet, 1));

    }

    public String getSubjectById(Long id){
        RowMapper<String> rowMapper = new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("subject");
            }
        };
        return jdbcTemplate.queryForObject(SQL_GET_SUBJ_BY_ID, rowMapper, id);
    }

    public Long getIdSubjectBySubject(String subject){
        RowMapper<Long> rowMapper = new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("id_subj");
            }
        };
        return jdbcTemplate.queryForObject(SQL_GET_SUBJ_BY_SUBJ, rowMapper, subject);
    }

    @Override
    public void save(Exam model) {
        jdbcTemplate.update(SQL_SAVE_EXAM, model.getUser().getId(), model.getScore());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_EXAM, id);
    }

    @Override
    public void update(Long id, Exam model) {
        jdbcTemplate.update(SQL_UPDATE_EXAM, model.getUser().getId(), model.getScore(), id);
    }

    @Override
    public List<Exam> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL_EXAMS, new ExamRowMapper());
    }
}
