package com.quizapp.demo.questionOption;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionOptionService {

    private final QuestionOptionRepository questionOptionRepository;

    public QuestionOptionService(QuestionOptionRepository questionOptionRepository) {
        this.questionOptionRepository = questionOptionRepository;
    }

    public List<QuestionOption> getQuestionOption() {
        return questionOptionRepository.findAll();
    }

    public Optional<QuestionOption> getQuestionByQuestionId(Long questionOptionId) {
        return questionOptionRepository.findById(questionOptionId);
    }

}
