package ru.erik182.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.erik182.mappers.DeclarationRowMapper;
import ru.erik182.mappers.ExamRowMapper;
import ru.erik182.mappers.UserRowMapper;
import ru.erik182.models.Declaration;
import ru.erik182.models.Exam;
import ru.erik182.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserRepositoryJdbcTemplateImpl implements UserRepository {


    private JdbcTemplate jdbcTemplate;
    private ExamRepository examRepository;

    private static final String SQL_SELECT_USER = "SELECT * FROM \"user\" WHERE user_id = ?;";

    private static final String SQL_SELECT_USER_BY_PASSPORT = "SELECT * FROM \"user\" WHERE passport = ? ;";

    private static final String SQL_GET_ALL_EXAMS_OF_USER = "SELECT score, subject, exam_id FROM \"user\"\n" +
            "JOIN exam e on \"user\".user_id = e.id_user\n" +
            "JOIN subject s2 on e.id_subj = s2.id_subj WHERE passport = ?;";

    private static final String SQL_GET_DIR_OF_USER = "SELECT id_dir FROM declaration\n" +
            "JOIN \"user\" u on declaration.id_user = u.user_id WHERE passport = ?";

    private static final String SQL_GET_DEC_OF_USER = "SELECT dec_id, declaration.id_user, full_name, d2.id_dir, d2.name, budget_places, university.name as uni_name, sum_score FROM declaration\n" +
            "JOIN \"user\" ON declaration.id_user = \"user\".user_id\n" +
            "JOIN direction d2 on declaration.id_dir = d2.id_dir\n" +
            "JOIN university ON d2.id_uni = university.id_uni\n" +
            "WHERE passport=?;";

    private final static String SQL_GET_ALL_USERS = "SELECT * FROM \"user\" ;";

    private final static String SQL_UPDATE_USER = "UPDATE \"user\" SET full_name = ?, passport = ? WHERE user_id = ?;";

    private final static String SQL_GET_ALL_CITIES = "SELECT * FROM city;";

    private static final String SQL_DELETE_USER = "DELETE FROM \"user\" WHERE user_id = ?;";

    private static final String SQL_DELETE_EXAMS_OF_USER = "DELETE FROM exam WHERE id_user=?;";

    private static final String SQL_DELETE_DECS_OF_USER = "DELETE FROM declaration WHERE id_user=?;";

    private final static String SQL_INSERT_USER = "INSERT INTO \"user\"(full_name, passport, password, type)\n" +
            "    VALUES (?,?,?,'user');";

    private final static String SQL_INSERT_EX_OF_USER = "INSERT INTO exam(id_user, id_subj, score) VALUES (?,?,?);";

    private final static String SQL_SAVE_CITY = "INSERT INTO city(city) VALUES(?);";



    public UserRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        examRepository = new ExamRepositoryJdbcTemplateImpl(dataSource);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_USER, new UserRowMapper(), id));
    }

    @Override
    public void save(User model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT_USER, new String[] {"user_id"});
                    ps.setString(1, model.getFullName());
                    ps.setString(2, model.getPassport());
                    ps.setString(3, model.getHashPassword());
                    return ps;
                }, keyHolder);

        model.setId(keyHolder.getKey().longValue());

        for(Exam exam: model.getExams()){
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps =
                                connection.prepareStatement(SQL_INSERT_EX_OF_USER);
                        ps.setLong(1, model.getId());
                        ps.setLong(2,examRepository.getIdSubjectBySubject(exam.getSubject()));
                        ps.setInt(3, exam.getScore());
                        return ps;
                    }, keyHolder);
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_EXAMS_OF_USER, id);
        jdbcTemplate.update(SQL_DELETE_DECS_OF_USER, id);
        jdbcTemplate.update(SQL_DELETE_USER, id);
    }

    @Override
    public void update(Long id, User model) {
        jdbcTemplate.update(SQL_UPDATE_USER, model.getFullName(), model.getPassport(), id);
    }

    @Override
    public List<User> findAll() {
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user;
                user = new UserRowMapper().mapRow(resultSet, i);
                user.setExams(getExamsOfUserByPassport(user.getPassport()));
                return user;
            }
        };
        return jdbcTemplate.query(SQL_GET_ALL_USERS, rowMapper);
    }

    @Override
    public Optional<User> getUserByPassport(String passport) {
        try{
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_PASSPORT, new UserRowMapper(), passport));
        }
        catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public List<Exam> getExamsOfUserByPassport(String passport){
        return jdbcTemplate.query(SQL_GET_ALL_EXAMS_OF_USER, new ExamRowMapper(), passport);
    }

    public List<Long> getIdsDirectionsOfUser(String passport){
        RowMapper rowMapper  = new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("id_dir");
            }
        };
        return jdbcTemplate.query(SQL_GET_DIR_OF_USER, rowMapper, passport );
    }

    public List<Declaration> getDeclarationsOfUser(String passport){
        return jdbcTemplate.query(SQL_GET_DEC_OF_USER, new DeclarationRowMapper(), passport);
    }

    public List<String> getAllCities(){
        RowMapper<String> rowMapper = new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("id_city")+" "+resultSet.getString("city");
            }
        };
        return jdbcTemplate.query(SQL_GET_ALL_CITIES, rowMapper);
    }

    public void saveCity(String city){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_SAVE_CITY, new String[]{"id_city"});
                    ps.setString(1, city);
                    return ps;
                }, keyHolder);
    }


}
