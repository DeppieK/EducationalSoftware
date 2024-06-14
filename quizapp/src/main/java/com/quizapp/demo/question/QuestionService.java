package com.quizapp.demo.question;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestion() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionByQuestionId(Long questionId) {
        return questionRepository.findById(questionId);
    }

}
