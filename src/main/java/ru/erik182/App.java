package ru.erik182;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.erik182.repositories.*;

import javax.servlet.ServletContextEvent;

public class App {

    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "rrr182";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";


    public static void main(String[] args) {
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        DirectionRepository directionRepository = new DirectionRepositoryJdbcTemplateImpl(dataSource);

        System.out.println(directionRepository.findOne(6L));

    }
}
