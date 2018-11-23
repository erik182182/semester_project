package ru.erik182.services;

import ru.erik182.models.Declaration;
import ru.erik182.models.Direction;
import ru.erik182.models.Exam;
import ru.erik182.models.User;
import ru.erik182.repositories.DeclarationRepository;
import ru.erik182.repositories.DirectionRepository;
import ru.erik182.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DirectionService {

    private DirectionRepository directionRepository;
    private UserRepository userRepository;
    private DeclarationRepository declarationRepository;

    public DirectionService(DirectionRepository directionRepository, UserRepository userRepository,
                            DeclarationRepository declarationRepository) {
        this.directionRepository = directionRepository;
        this.userRepository = userRepository;
        this.declarationRepository = declarationRepository;
    }


    public List<Direction> getAllDirections(){
        return directionRepository.findAll();
    }

    public List<Direction> getDirectionsForUser(String passport){
        List<Exam> userExams = userRepository.getExamsOfUserByPassport(passport);


        List<Direction> directions = getAllDirections();

        List<Direction> userDirections = new ArrayList<>();

        int matches = 0;
        for(Direction direction: directions){
            List<Exam> dirExams = direction.getExamsWithMinScore();
            for(Exam userExam: userExams){
                for(Exam dirExam:dirExams){
                    if(userExam.getSubject().equals(dirExam.getSubject())
                            && userExam.getScore()>= dirExam.getScore()){
                        matches++;
                        break;
                    }

                }
            }
            if (matches==dirExams.size()) userDirections.add(direction);
            matches = 0;
        }

        return userDirections;

    }


}
