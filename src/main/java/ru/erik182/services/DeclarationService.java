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

public class DeclarationService {

    private DirectionRepository directionRepository;
    private UserRepository userRepository;
    private DeclarationRepository declarationRepository;

    public DeclarationService(DirectionRepository directionRepository, UserRepository userRepository,
                            DeclarationRepository declarationRepository) {
        this.directionRepository = directionRepository;
        this.userRepository = userRepository;
        this.declarationRepository = declarationRepository;
    }


    public void saveDeclarationOfUser(String userPassport, Long dirId){
        Optional<User> userCandidate = userRepository.getUserByPassport(userPassport);
        if(userCandidate.isPresent()){
            User user = userCandidate.get();
            int sumScore = 0;
            List<Exam> exams = userRepository.getExamsOfUserByPassport(userPassport);
            if(!exams.isEmpty()){


                checkDeclaration(userPassport, dirId);


                for(Exam userExam:exams){
                    for(Exam dirExam: directionRepository.getExamsOfDirectionById(dirId)){
                        if (userExam.getSubject()
                                .equals(dirExam.getSubject())) sumScore += userExam.getScore();
                    }
                }
                declarationRepository.save(Declaration.builder()
                        .direction(Direction.builder().id(dirId).build())
                        .user(user)
                        .sumScore(sumScore)
                        .build());
            }
            else throw new IllegalArgumentException("Вы не сдали ни одного экзамена.");
        }
        else throw new IllegalArgumentException("Такого пользователя не существует.");
    }

    private void checkDeclaration(String passport, long dirId){
        List<Long> ids = userRepository.getIdsDirectionsOfUser(passport);

        if(ids.size() >= 3) throw new IllegalArgumentException("Вы подали максимальное количество заявлений.");

        if(ids.contains(dirId)) throw new IllegalArgumentException("Вы уже подали заявление на это навправление");
    }

    public List<Declaration> getDeclarationsOfUser(String passport){
        return userRepository.getDeclarationsOfUser(passport);
    }

    public List<Direction> getDirectionOfUser(String passport){
        List<Long> idsDir = userRepository.getIdsDirectionsOfUser(passport);
        List<Direction> dirs = new ArrayList<>();

        for(Long id: idsDir){
            Direction direction = directionRepository.findOne(id).get();
            List<Declaration> declarations = directionRepository.getDeclarationsOfDirectionById(id);
            declarations.sort( (s1,s2) -> {
                return s2.getSumScore() - s1.getSumScore();
            });
            direction.setDeclarations(declarations);

            dirs.add(direction);
        }

        return dirs;
    }

}
