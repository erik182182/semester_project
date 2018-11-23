package ru.erik182.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.erik182.models.Direction;
import ru.erik182.models.Exam;
import ru.erik182.models.University;
import ru.erik182.models.User;
import ru.erik182.repositories.DirectionRepository;
import ru.erik182.repositories.ExamRepository;
import ru.erik182.repositories.UniversityRepository;
import ru.erik182.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminService {
    private UserRepository usersRepository;
    private UniversityRepository universityRepository;
    private DirectionRepository directionRepository;
    private ExamRepository examRepository;
    private PasswordEncoder encoder;

    public AdminService(UserRepository usersRepository, UniversityRepository universityRepository, DirectionRepository directionRepository, ExamRepository examRepository) {
        this.encoder = new BCryptPasswordEncoder();
        this.usersRepository = usersRepository;
        this.universityRepository = universityRepository;
        this.directionRepository = directionRepository;
        this.examRepository = examRepository;
    }

    public List<User> getAllUsers(){
        List<User> users =  usersRepository.findAll();
        List<User> res = new ArrayList<>();

        for(User user:users){
            if(user.getType().equals("user")) res.add(user);
        }

        return res;
    }

    public List<String> getAllCities(){
        return usersRepository.getAllCities();
    }

    public List<University> getAllUniversities(){
        return universityRepository.findAll();
    }

    public List<Direction> getAllDirections(){
         return directionRepository.findAll();
    }




    public void saveUser(HttpServletRequest req){
        User user = User.builder()
                .fullName(req.getParameter("name"))
                .passport(req.getParameter("passport"))
                .hashPassword(encoder.encode(req.getParameter("password")) )
                .build();
        List<Exam> exams = new ArrayList<>();
        for(long i = 1; i<=9; i++){
            if(!req.getParameter("subj-"+i).equals("")){
                exams.add(Exam.builder()
                        .score(Integer.valueOf(req.getParameter("subj-"+i)))
                        .subject(examRepository.getSubjectById(i))
                        .build());

            }
        }
        user.setExams(exams);
        System.out.println(user);
        try{
            usersRepository.save(user);
            req.setAttribute("message", "Успешно сохранено.");
        }
        catch(IllegalArgumentException e){
            req.setAttribute("message", e.getMessage());
        }
    }

    public void deleteUser(HttpServletRequest req){
        Optional<User> userCandidate = usersRepository.getUserByPassport(req.getParameter("passport"));
        if(userCandidate.isPresent()){
            usersRepository.delete(userCandidate.get().getId());
        } else{
            throw new IllegalArgumentException("Пользователя с таким ID не существует.");
        }
    }

    public void saveCity(HttpServletRequest req) {
        usersRepository.saveCity(req.getParameter("city"));
    }

    public void saveUniversity(HttpServletRequest req){
        University university = University.builder()
                .city(req.getParameter("city"))
                .name(req.getParameter("uni"))
                .info(req.getParameter("info"))
                .build();

        universityRepository.save(university);
    }

    public void saveDirection(HttpServletRequest req){
        Direction direction = Direction.builder()
                .name(req.getParameter("dir"))
                .university(University.builder().id(Long.valueOf(req.getParameter("uni"))).build())
                .info(req.getParameter("info"))
                .budgetPlaces(Integer.valueOf(req.getParameter("budget")))
                .build();
        List<Exam> exams = new ArrayList<>();
        for(long i = 1; i<=9; i++){
            if(!req.getParameter("subj-"+i).equals("")){
                exams.add(Exam.builder()
                        .score(Integer.valueOf(req.getParameter("subj-"+i)))
                        .subject(examRepository.getSubjectById(i))
                        .build());

            }
        }

        direction.setExamsWithMinScore(exams);

        directionRepository.save(direction);
    }
}
