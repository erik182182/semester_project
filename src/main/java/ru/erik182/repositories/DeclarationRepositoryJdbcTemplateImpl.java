package ru.erik182.repositories;

import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.erik182.mappers.DeclarationRowMapper;
import ru.erik182.models.Declaration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeclarationRepositoryJdbcTemplateImpl implements DeclarationRepository {

    private JdbcTemplate jdbcTemplate;


    private static final String SQL_GET_DEC = "SELECT * FROM declaration WHERE dec_id = ?;";

    private static final String SQL_SAVE_DEC = "INSERT INTO declaration(id_user, id_dir, sum_score)\n" +
            "    VALUES (?,?,?);";

    private static final String SQL_DELETE_DEC = "DELETE FROM declaration WHERE dec_id = ?;";

    private static final String SQL_UPDATE_DEC = "UPDATE declaration SET id_dir = ?, id_user = ?, sum_score = ?\n" +
            "WHERE dec_id = ?;";

    private static final String SQL_GET_ALL_DECS = "SELECT * FROM declaration ;";

    public DeclarationRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Optional<Declaration> findOne(Long id) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_GET_DEC, new DeclarationRowMapper(), id));
    }

    @Override
    @SneakyThrows
    public void save(Declaration model) {
//        jdbcTemplate.update(SQL_SAVE_DEC, model.getUser().getId(),
//                model.getDirection().getId(), model.getSumScore());

        PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQL_SAVE_DEC);
        preparedStatement.setLong(1, model.getUser().getId());
        preparedStatement.setLong(2, model.getDirection().getId());
        preparedStatement.setInt(3, model.getSumScore());
        preparedStatement.executeUpdate();
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
//        jdbcTemplate.update(SQL_DELETE_DEC, id);
        PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQL_DELETE_DEC);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    @SneakyThrows
    public void update(Long id, Declaration model) {
//        jdbcTemplate.update(SQL_UPDATE_DEC, model.getDirection().getId(),
//                model.getUser().getId(), model.getSumScore(), id);

        PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQL_UPDATE_DEC);
        preparedStatement.setLong(1, model.getDirection().getId());
        preparedStatement.setLong(2, model.getUser().getId());
        preparedStatement.setInt(3, model.getSumScore());
        preparedStatement.setLong(4, id);
        preparedStatement.executeUpdate();
    }

    @Override
    @SneakyThrows
    public List<Declaration> findAll() {

//        return jdbcTemplate.query(SQL_GET_ALL_DECS, new DeclarationRowMapper());
        PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQL_GET_ALL_DECS);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        List<Declaration> declarations = new ArrayList<>();
        while(resultSet.next()){
            declarations.add(new DeclarationRowMapper().mapRow(resultSet, 1));
        }
        return declarations;
    }


}
