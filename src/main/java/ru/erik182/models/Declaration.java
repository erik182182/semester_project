package ru.erik182.models;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder

public class Declaration {

    private Long id;
    private User user;
    private Direction direction;
    private int sumScore;

}
