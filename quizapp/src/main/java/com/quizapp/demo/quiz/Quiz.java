package com.quizapp.demo.quiz;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Setter
@Getter
@SpringBootApplication
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @SequenceGenerator(
            name = "quiz_sequence",
            sequenceName = "quiz_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "quiz_sequence"
    )
    private Long quizId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column
    private int label;

    @Column
    private String unit;


    // Constructors
    public Quiz() {
    }

    public Quiz(Long quizId) {
        this.quizId = quizId;
    }

    public Quiz(Long quizId, String title, Difficulty difficulty, int label, String unit) {
        this.quizId = quizId;
        this.title = title;
        this.difficulty = difficulty;
        this.label = label;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", title='" + title + '\'' +
                ", difficulty=" + difficulty +
                ", label=" + label +
                ", unit='" + unit + '\'' +
                '}';
    }

    public enum Difficulty {
        BEGINNER,INTERMEDIATE,EXPERT,NONE
    }
}
