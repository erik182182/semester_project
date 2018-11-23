package ru.erik182.servlets;

import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse res){
        req.getSession().setAttribute("user", null);
        Cookie loginCookie = null;
        Cookie authCookie = null;
        if(req.getCookies() != null){
            for(Cookie cookie:req.getCookies()){
                if(cookie.getName().equals("login")) loginCookie = cookie;
                if(cookie.getName().equals("auth")) authCookie = cookie;
            }
            if(loginCookie != null){
                loginCookie.setMaxAge(0);
                res.addCookie(loginCookie);
            }
            if(authCookie != null){
                authCookie.setMaxAge(0);
                res.addCookie(authCookie);
            }
        }
        res.sendRedirect("/home");
    }
}
