package ru.erik182.context;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.erik182.repositories.*;
import ru.erik182.services.DeclarationService;
import ru.erik182.services.DirectionService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DeclarationServiceListener implements ServletContextListener {
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

        DirectionRepository directionRepository = new DirectionRepositoryJdbcTemplateImpl(dataSource);
        UserRepository userRepository = new UserRepositoryJdbcTemplateImpl(dataSource);
        DeclarationRepository declarationRepository = new DeclarationRepositoryJdbcTemplateImpl(dataSource);

        DeclarationService declarationService = new DeclarationService(directionRepository, userRepository, declarationRepository);
        ServletContext context = sce.getServletContext();
        context.setAttribute("declarationService", declarationService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
