package com.quizapp.demo.questionAttempt;

import com.quizapp.demo.question.Question;
import com.quizapp.demo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Setter
@Getter
@SpringBootApplication
@Entity
@Table(name = "question_attempts")
public class QuestionAttempt {

    @Id
    @SequenceGenerator(
            name = "question_attempt_sequence",
            sequenceName = "question_attempt_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_attempt_sequence"
    )
    private Long questionAttemptId;

    @Column(name = "attempt", nullable = false)
    @NotNull
    private Integer attempt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors, getters, setters, toString

    public QuestionAttempt() {}

    public QuestionAttempt(Long questionAttemptId, Integer attempt, User user, Question question) {
        this.questionAttemptId = questionAttemptId;
        this.attempt = (attempt != null) ? attempt : 0;
        this.user = user;
        this.question = question;
    }

    @Override
    public String toString() {
        return "QuestionAttempt{" +
                "questionAttemptId=" + questionAttemptId +
                ", attempt=" + attempt +
                ", question=" + question +
                ", user=" + user +
                '}';
    }
}
