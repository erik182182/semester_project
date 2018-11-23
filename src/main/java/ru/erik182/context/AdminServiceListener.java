package ru.erik182.context;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.erik182.repositories.*;
import ru.erik182.services.AdminService;
import ru.erik182.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AdminServiceListener implements ServletContextListener {
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
        UniversityRepository universityRepository = new UniversityRepositoryJdbcTemplateImpl(dataSource);
        DirectionRepository directionRepository = new DirectionRepositoryJdbcTemplateImpl(dataSource);
        ExamRepository examRepository = new ExamRepositoryJdbcTemplateImpl(dataSource);
        AdminService adminService = new AdminService(userRepository, universityRepository, directionRepository, examRepository);
        ServletContext context = sce.getServletContext();
        context.setAttribute("adminService", adminService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
