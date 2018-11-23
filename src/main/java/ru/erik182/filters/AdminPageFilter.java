package ru.erik182.filters;

import ru.erik182.models.User;
import ru.erik182.services.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminPageFilter implements Filter {

    private UserService userService;

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");


        if(user == null || user.getType().equals("user")){
            res.setStatus(403);
            return;
        }
        else {
            filterChain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
