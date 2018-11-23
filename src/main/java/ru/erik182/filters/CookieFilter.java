package ru.erik182.filters;

import ru.erik182.models.User;
import ru.erik182.services.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CookieFilter implements Filter {

    private final static String SALT = "435fg gffsdgh fghgf";
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userService = (UserService)filterConfig.getServletContext().getAttribute("userService");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            Cookie authCookie = null;
            Cookie loginCookie = null;

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for(Cookie cookie:cookies){
                    if(cookie.getName().equals("auth")) authCookie = cookie;
                    if(cookie.getName().equals("login")) loginCookie = cookie;
                }
            }
            if(loginCookie != null && authCookie != null &&
                    String.valueOf(loginCookie.getValue().concat(SALT).hashCode())
                            .equals(authCookie.getValue())){
                session.setAttribute("user", userService.getUserByPassport(loginCookie.getValue()));
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
