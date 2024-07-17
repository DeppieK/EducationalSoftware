package com.quizapp.demo.question;

import com.quizapp.demo.questionOption.QuestionOption;
import com.quizapp.demo.quiz.Quiz;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Setter
@Getter
@SpringBootApplication
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "alternative_question_id")
    private Question alternativeQuestion;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> questionOptions;

    // Constructors
    public Question() {
    }

    public Question(Long questionId) {
        this.questionId = questionId;
    }

    public Question(Long questionId, Quiz quiz, String questionText, Question alternativeQuestion) {
        this.questionId = questionId;
        this.quiz = quiz;
        this.questionText = questionText;
        this.alternativeQuestion = alternativeQuestion;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", quiz=" + quiz +
                ", questionText='" + questionText + '\'' +
                ", alternativeQuestion=" + alternativeQuestion +
                '}';
    }

}
