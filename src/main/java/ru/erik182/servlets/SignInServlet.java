package ru.erik182.servlets;

import ru.erik182.forms.LoginForm;
import ru.erik182.models.User;
import ru.erik182.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private UserService userService;

    private final static String SALT = "435fg gffsdgh fghgf";

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService)context.getAttribute("userService");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(req.getSession().getAttribute("user") != null){
            if(((User)req.getSession().getAttribute("user")).getType().equals("user")){
                res.sendRedirect("/home");
                return;
            }
            else{
                res.sendRedirect("/adminPage");
                return;
            }
        }
        else req.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        LoginForm loginForm = LoginForm.builder()
                .passport(req.getParameter("passport"))
                .password(req.getParameter("password"))
                .build();

        try{
            userService.signIn(loginForm);
            req.getSession().setAttribute("user", userService.getUserByPassport(loginForm.getPassport()));
        }catch(IllegalArgumentException e){
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req,res);
            return;
        }
        if(req.getParameter("remember") != null){
            Cookie authCookie = new Cookie("auth", String.valueOf(req.getParameter("passport").concat(SALT).hashCode()));
            authCookie.setMaxAge(60 * 60 * 24 * 365 * 3);
            res.addCookie(authCookie);

            Cookie loginCookie = new Cookie("login", req.getParameter("passport") );
            loginCookie.setMaxAge(60 * 60 * 24 * 365 * 3);
            res.addCookie(loginCookie);
        }
        if(((User)req.getSession().getAttribute("user")).getType().equals("user")){
            res.sendRedirect("/home");
        }
        else{
            res.sendRedirect("/adminPage");
        }

    }
}
