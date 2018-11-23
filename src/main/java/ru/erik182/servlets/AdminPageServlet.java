package ru.erik182.servlets;

import ru.erik182.forms.LoginForm;
import ru.erik182.models.User;
import ru.erik182.services.AdminService;
import ru.erik182.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {
    private AdminService adminService;

    private final static String SALT = "435fg gffsdgh fghgf";

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        adminService = (AdminService)context.getAttribute("adminService");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("users",adminService.getAllUsers());
        req.setAttribute("cities",adminService.getAllCities());
        req.setAttribute("unis",adminService.getAllUniversities());
        req.setAttribute("dirs",adminService.getAllDirections());

        req.getRequestDispatcher("/WEB-INF/views/adminpage.jsp").forward(req,res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        switch (req.getParameter("form")){
            case "1":{
                adminService.saveUser(req);
            }break;
            case "2":{
                adminService.deleteUser(req);
            }break;
            case "3":{
                adminService.saveCity(req);
            }break;
            case "4":{
                adminService.saveUniversity(req);
            }break;
            case "5":{
                adminService.saveDirection(req);
            }break;
        }
        req.getRequestDispatcher("/WEB-INF/views/adminpage.jsp").forward(req,res);
    }
}
