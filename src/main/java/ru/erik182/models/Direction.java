package ru.erik182.models;

import java.util.List;
import java.util.Map;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder

public class Direction {
    private Long id;
    private String name;
    private String info;
    private int budgetPlaces;
    private List<Declaration> declarations;
    private University university;
    private List<Exam> examsWithMinScore;
}
