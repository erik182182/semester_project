package ru.erik182.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.erik182.mappers.DirectionExamRowMapper;
import ru.erik182.mappers.ExamRowMapper;
import ru.erik182.mappers.UniversityRowMapper;
import ru.erik182.models.*;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DirectionRepositoryJdbcTemplateImpl implements DirectionRepository {

    private JdbcTemplate jdbcTemplate;
    private ExamRepository examRepository;

    private static final String SQL_GET_ALL_DIR = "SELECT id_dir,direction.name, direction.info as dir_info, u.id_uni, u.name as uni_name, u.info as uni_info, city, budget_places FROM direction\n" +
            "JOIN university u on direction.id_uni = u.id_uni\n" +
            "JOIN city c2 on u.id_city = c2.id_city;";

    private static final String SQL_GET_EXAMS_OF_DIR = "SELECT subject, min_score FROM direction\n" +
            "JOIN score_info s2 on direction.id_dir = s2.id_dir\n" +
            "JOIN subject s3 on s2.id_subj = s3.id_subj WHERE direction.id_dir = ?";

    private static final String SQL_GET_DIR = "SELECT id_dir, direction.name, u.name as uni_name, budget_places  FROM direction\n" +
            "JOIN university u on direction.id_uni = u.id_uni\n" +
            "WHERE direction.id_dir = ?;";

    private static final String SQL_GET_DEC_OF_DIR = "SELECT full_name,sum_score FROM direction\n" +
            "JOIN declaration d2 on direction.id_dir = d2.id_dir\n" +
            "JOIN \"user\" ON d2.id_user = \"user\".user_id WHERE d2.id_dir = ?;";

    private static final String SQL_INSERT_DIR = "INSERT INTO direction(name, id_uni, info, budget_places) \n" +
            "    VALUES (?,?,?,?);";

    private static final String SQL_INSERT_EXAMS_OF_DIR = "INSERT INTO score_info(id_dir, id_subj, min_score)" +
            " VALUES (?,?,?);";

    private static final String SQL_DELETE_DIR = "DELETE FROM direction WHERE id_dir = ?;";

    private static final String SQL_UPDATE_DIR = "UPDATE direction SET name = ?, id_uni = ?, budget_places = ?, info = ?\n" +
            "WHERE id_dir = ?;";

    public DirectionRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        examRepository = new ExamRepositoryJdbcTemplateImpl(dataSource);
    }

    @Override
    public Optional<Direction> findOne(Long id) {
        RowMapper<Direction> rowMapper = new RowMapper<Direction>() {
            @Override
            public Direction mapRow(ResultSet resultSet, int i) throws SQLException {
                return Direction.builder()
                        .name(resultSet.getString("name"))
                        .university(University.builder().name(resultSet.getString("uni_name")).build())
                        .id(resultSet.getLong("id_dir"))
                        .budgetPlaces(resultSet.getInt("budget_places"))
                        .build();
            }
        };
        return Optional.of(jdbcTemplate.queryForObject(SQL_GET_DIR, rowMapper, id));
    }

    @Override
    public void save(Direction model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_DIR, new String[] {"id_dir"});
                    ps.setString(1, model.getName());
                    ps.setLong(2, model.getUniversity().getId());
                    ps.setString(3, model.getInfo());
                    ps.setInt(4,model.getBudgetPlaces());
                    return ps;
                }, keyHolder);

        model.setId(keyHolder.getKey().longValue());

        for(Exam exam: model.getExamsWithMinScore()){
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps =
                                connection.prepareStatement(SQL_INSERT_EXAMS_OF_DIR);
                        ps.setLong(1, model.getId());
                        ps.setLong(2,examRepository.getIdSubjectBySubject(exam.getSubject()));
                        ps.setInt(3, exam.getScore());
                        return ps;
                    }, keyHolder);
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_DIR, id);
    }

    @Override
    public void update(Long id, Direction model) {
        jdbcTemplate.update(SQL_UPDATE_DIR, model.getName(), model.getUniversity().getId(),
                model.getBudgetPlaces(), model.getInfo(), id);
    }

    @Override
    public List<Direction> findAll() {
        RowMapper<Direction> rowMapper = new RowMapper<Direction>() {
            @Override
            public Direction mapRow(ResultSet resultSet, int i) throws SQLException {
                return Direction.builder()
                        .id(resultSet.getLong("id_dir"))
                        .name(resultSet.getString("name"))
                        .info(resultSet.getString("dir_info"))
                        .budgetPlaces(resultSet.getInt("budget_places"))
                        .university(new UniversityRowMapper().mapRow(resultSet, i))
                        .examsWithMinScore(new DirectionRepositoryJdbcTemplateImpl(jdbcTemplate.getDataSource())
                                .getExamsOfDirectionById(resultSet.getLong("id_dir")))
                        .build();
            }
        };
        return jdbcTemplate.query(SQL_GET_ALL_DIR, rowMapper);
    }

    public List<Exam> getExamsOfDirectionById(Long id){
        return jdbcTemplate.query(SQL_GET_EXAMS_OF_DIR, new DirectionExamRowMapper(), id);
    }

    public List<Declaration> getDeclarationsOfDirectionById(Long id){
        RowMapper rowMapper = new RowMapper<Declaration>() {
            @Override
            public Declaration mapRow(ResultSet resultSet, int i) throws SQLException {
                return Declaration.builder()
                        .sumScore(resultSet.getInt("sum_score"))
                        .user(User.builder().fullName(resultSet.getString("full_name")).build())
                        .build();
            }

        };
        return jdbcTemplate.query(SQL_GET_DEC_OF_DIR, rowMapper, id);
    }

}
