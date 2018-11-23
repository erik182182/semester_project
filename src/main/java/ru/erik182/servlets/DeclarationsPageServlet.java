package ru.erik182.servlets;

import ru.erik182.models.User;
import ru.erik182.services.DeclarationService;
import ru.erik182.services.DirectionService;
import ru.erik182.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class DeclarationsPageServlet extends HttpServlet {


    private UserService userService;
    private DirectionService directionService;
    private DeclarationService declarationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        directionService = (DirectionService) context.getAttribute("directionService");
        declarationService = (DeclarationService) context.getAttribute("declarationService");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        req.setAttribute("name", user.getFullName());

        req.setAttribute("dirs", declarationService.getDirectionOfUser(user.getPassport()));

        req.getRequestDispatcher("/WEB-INF/views/declarations.jsp").forward(req, res);

    }
}
