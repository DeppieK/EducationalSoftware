package com.quizapp.demo.quiz;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuiz() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizbyId(Long quizId) {
        return quizRepository.findById(quizId);
    }
}
