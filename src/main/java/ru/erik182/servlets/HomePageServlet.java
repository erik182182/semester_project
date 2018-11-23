package ru.erik182.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import ru.erik182.models.Declaration;
import ru.erik182.models.Direction;
import ru.erik182.models.Exam;
import ru.erik182.models.User;
import ru.erik182.services.DeclarationService;
import ru.erik182.services.DirectionService;
import ru.erik182.services.UserService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class HomePageServlet extends HttpServlet {

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

        if(user != null) req.setAttribute("name", user.getFullName());

        List<Direction> directions = null;
        List<Declaration> declarations = new ArrayList<>();
        if(user != null){
            List<Exam> exams = userService.getExamsOfUserByPassport(user.getPassport());
            if(!exams.isEmpty()){
                req.setAttribute("exams", exams);
            }

            directions = directionService.getDirectionsForUser(user.getPassport());
            declarations = declarationService.getDeclarationsOfUser(user.getPassport());
            req.setAttribute("declarations", declarations);
            req.setAttribute("size", declarations.size());
            req.setAttribute("directions", directions);
        }
        else{
            directions = directionService.getAllDirections();
            req.setAttribute("directions", directions);
        }
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req,res);

    }


    private ObjectMapper objectMapper = new ObjectMapper();

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        Cookie loginCookie = null;
//        if(req.getCookies() != null){
//            for(Cookie cookie: req.getCookies()){
//                if(cookie.getName().equals("login")) loginCookie = cookie;
//            }
//        }
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        try{
            declarationService.saveDeclarationOfUser(user.getPassport(),
                    Long.valueOf(req.getParameter("dirId")) );
            JSONObject json = new JSONObject();
            json.put("message", "Заявление успешно сохранено.");
            json.put("flag", "success");
            List<String> decs = new ArrayList<>();
            for(Declaration declaration:declarationService.getDeclarationsOfUser(user.getPassport()) ){
                decs.add(declaration.getDirection().getName() );
            }
            json.put("size", decs.size());
            json.put("declarations", decs);
            res.setContentType("application/json; charset=utf-8");
            res.getWriter().write(json.toJSONString());

        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            JSONObject json = new JSONObject();
            json.put("message", e.getMessage());
            json.put("flag", "danger");
            res.setContentType("application/json; charset=utf-8");
            res.getWriter().write(json.toJSONString());
        }
    }

}
