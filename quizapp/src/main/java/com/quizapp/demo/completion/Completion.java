package com.quizapp.demo.completion;

import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "completions")
public class Completion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int completed;

    //constructors
    public Completion() {
        this.completed = 0; //set default value
    }

    public Completion(User user, Quiz quiz, int completed) {
        this.user = user;
        this.quiz = quiz;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Completion{" +
                "id=" + id +
                ", user=" + user +
                ", quiz=" + quiz +
                ", completed=" + completed +
                '}';
    }

}
