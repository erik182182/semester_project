package ru.erik182.models;

import java.util.Set;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder

public class University {
    private long id;
    private String city;
    private String name;
    private String info;
    private Set<Direction> directions;
}
