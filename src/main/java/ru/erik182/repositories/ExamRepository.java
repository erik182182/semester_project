package ru.erik182.repositories;

import ru.erik182.models.Exam;

public interface ExamRepository extends CrudRepository<Exam>{
    String getSubjectById(Long id);
    Long getIdSubjectBySubject(String subject);
}
