package com.quizapp.demo.questionOption;

import com.quizapp.demo.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Setter
@Getter
@SpringBootApplication
@Entity
@Table(name = "question_options")
public class QuestionOption {

    @Id
    @SequenceGenerator(
            name = "option_sequence",
            sequenceName = "option_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "option_sequence"
    )
    private Long optionId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "option_text", nullable = false, columnDefinition = "TEXT")
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    // Constructors
    public QuestionOption() {
    }

    public QuestionOption(Long optionId) {
        this.optionId = optionId;
    }

    public QuestionOption(Long optionId, Question question, String optionText, Boolean isCorrect) {
        this.optionId = optionId;
        this.question = question;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "QuestionOption{" +
                "optionId=" + optionId +
                ", question=" + question +
                ", optionText='" + optionText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
