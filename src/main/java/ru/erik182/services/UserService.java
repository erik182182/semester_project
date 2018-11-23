package ru.erik182.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.erik182.forms.LoginForm;
import ru.erik182.models.Declaration;
import ru.erik182.models.Direction;
import ru.erik182.models.Exam;
import ru.erik182.models.User;
import ru.erik182.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService{

    private UserRepository usersRepository;
    private PasswordEncoder encoder;

    public UserService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public void signIn(LoginForm loginForm){
        Optional<User> userCandidate = usersRepository.getUserByPassport(loginForm.getPassport());
        if(userCandidate.isPresent()){
            User user = userCandidate.get();
            if(!encoder.matches(loginForm.getPassword(), user.getHashPassword())){
                throw new IllegalArgumentException("Неверные данные.");
            }
        }
        else{
            throw new IllegalArgumentException("Такого абитуриента не существует.");
        }
    }


    public List<Exam> getExamsOfUserByPassport(String passport){
         return usersRepository.getExamsOfUserByPassport(passport);
    }

    public User getUserByPassport(String passport){
        return usersRepository.getUserByPassport(passport).get();
    }


}
