package ru.erik182.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.erik182.mappers.UniversityRowMapper;
import ru.erik182.models.University;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UniversityRepositoryJdbcTemplateImpl implements UniversityRepository {

    private JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;

    private static final String SQL_GET_UNI_BY_ID = "SELECT * FROM university WHERE id_uni = ?;";

    private static final String SQL_GET_ALL_UNI = "SELECT * FROM university JOIN city c2 on university.id_city = c2.id_city";

    private static final String SQL_SAVE_UNI = "\n" +
            "INSERT INTO university(name, id_city, info) \n" +
            "    VALUES (?,?,?);";

    private static final String SQL_UPDATE_UNI = "UPDATE university SET name = ?, info = ? WHERE id_uni = ?;";

    private static final String SQL_DELETE_UNI = "DELETE FROM university WHERE id_uni = ?;";

    public UniversityRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        userRepository = new UserRepositoryJdbcTemplateImpl(dataSource);
    }

    @Override
    public Optional<University> findOne(Long id) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_GET_UNI_BY_ID, new UniversityRowMapper()));
    }

    @Override
    public void save(University model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_SAVE_UNI, new String[] {"id_uni"});
                    ps.setString(1, model.getName());
                    ps.setLong(2, Long.valueOf(model.getCity()));
                    ps.setString(3, model.getInfo());
                    return ps;
                }, keyHolder);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_UNI, id);
    }

    @Override
    public void update(Long id, University model) {
        jdbcTemplate.update(SQL_UPDATE_UNI, model.getName(), model.getInfo(), id);
    }

    @Override
    public List<University> findAll() {
        RowMapper rowMapper = new RowMapper<University>() {
            @Override
            public University mapRow(ResultSet resultSet, int i) throws SQLException {
                return University.builder()
                        .id(resultSet.getLong("id_uni"))
                        .name(resultSet.getString("name"))
                        .city(resultSet.getString("city"))
                        .info(resultSet.getString("info"))
                        .build();
            }
        };
        return jdbcTemplate.query(SQL_GET_ALL_UNI, rowMapper);
    }

}
