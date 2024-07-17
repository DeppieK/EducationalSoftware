package com.quizapp.demo.user;

import com.quizapp.demo.quiz.Quiz;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Setter
@Getter
@SpringBootApplication
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    private String firstname;
    private String lastname;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Quiz.Difficulty level;

    private int totalScore;

    private String email;
    private LocalDateTime dateOfRegistration;
    private LocalDateTime lastLogIn;

    //Constructors
    public User() {

    }


    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String firstname, String lastname, String username, String password, Quiz.Difficulty level, int totalScore, String email, LocalDateTime dateOfRegistration, LocalDateTime lastLogIn) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.level = level;
        this.totalScore = totalScore;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
        this.lastLogIn = lastLogIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", totalScore=" + totalScore +
                ", level='" + level + '\'' +
                ", email='" + email + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                ", lastLogIn=" + lastLogIn +
                '}';
    }

}