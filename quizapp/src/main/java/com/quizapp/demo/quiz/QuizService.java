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

    public Optional<Quiz> getQuizById(Long quizId) {
        return quizRepository.findById(quizId);
    }

    public List<Quiz> findByUnit(String unit) {
        return quizRepository.findByUnit(unit);
    }
}
