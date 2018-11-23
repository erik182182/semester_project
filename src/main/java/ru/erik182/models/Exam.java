package ru.erik182.models;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder

public class Exam {
    private Long id;
    private User user;
    private String subject;
    private int score;
}
