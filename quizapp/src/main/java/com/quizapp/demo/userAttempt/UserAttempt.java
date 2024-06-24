package com.quizapp.demo.userAttempt;

import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Setter
@Getter
@SpringBootApplication
@Entity
@Table(name = "user_attempts")
public class UserAttempt {
    @Id
    @SequenceGenerator(
            name = "user_attempt_sequence",
            sequenceName = "user_attempt_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_attempt_sequence"
    )
    private Long attemptId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;


    @Column(name = "score")
    private int score;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    // Constructors
    public UserAttempt() {
    }

    public UserAttempt(Long attemptId) {
        this.attemptId = attemptId;
    }

    public UserAttempt(Long attemptId, User user, Quiz quiz, int score, Status status, LocalDateTime startTime, LocalDateTime endTime) {
        this.attemptId = attemptId;
        this.user = user;
        this.quiz = quiz;
        this.score = score;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "UserAttempt{" +
                "attemptId=" + attemptId +
                ", user=" + user +
                ", quiz=" + quiz +
                ", score=" + score +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public enum Status {
        PASSED, FAILED
    }
}
