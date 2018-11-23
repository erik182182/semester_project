package ru.erik182.repositories;

import ru.erik182.models.Declaration;
import ru.erik182.models.Direction;
import ru.erik182.models.Exam;

import java.util.List;

public interface DirectionRepository extends CrudRepository<Direction> {
    List<Exam> getExamsOfDirectionById(Long id);
    List<Declaration> getDeclarationsOfDirectionById(Long id);
}
