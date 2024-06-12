package com.quizapp.demo.questionAttempt;

import com.quizapp.demo.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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

    @Column(name = "attempt_id", nullable = false)
    private Integer attemptId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    //do we need this?
    @Column(name = "user_response", columnDefinition = "TEXT")
    private String userResponse;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    // Constructors
    public QuestionAttempt() {
    }

    public QuestionAttempt(Long questionAttemptId) {
        this.questionAttemptId = questionAttemptId;
    }

    public QuestionAttempt(Long questionAttemptId, Integer attemptId, Question question, String userResponse, Boolean isCorrect) {
        this.questionAttemptId = questionAttemptId;
        this.attemptId = attemptId;
        this.question = question;
        this.userResponse = userResponse;
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "QuestionAttempt{" +
                "questionAttemptId=" + questionAttemptId +
                ", attemptId=" + attemptId +
                ", question=" + question +
                ", userResponse='" + userResponse + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
