package ru.erik182.context;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.erik182.repositories.UserRepository;
import ru.erik182.repositories.UserRepositoryJdbcTemplateImpl;
import ru.erik182.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class UserServiceListener implements ServletContextListener {


    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "rrr182";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        UserRepository userRepository = new UserRepositoryJdbcTemplateImpl(dataSource);
        UserService userService = new UserService(userRepository);
        ServletContext context = sce.getServletContext();
        context.setAttribute("userService", userService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
