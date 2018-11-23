package ru.erik182.forms;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class LoginForm {
    private String passport;
    private String password;
}
