package com.quizapp.demo.userQuizStatus;

import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_quiz_status")
public class UserQuizStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private boolean enabled;

    public UserQuizStatus() {}

    public UserQuizStatus(User user, Quiz quiz, boolean enabled) {
        this.user = user;
        this.quiz = quiz;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UserQuizStatus{" +
                "id=" + id +
                ", user=" + user +
                ", quiz=" + quiz +
                ", enabled=" + enabled +
                '}';
    }
}
