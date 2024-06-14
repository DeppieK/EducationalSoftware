package com.quizapp.demo.quiz;

import com.quizapp.demo.question.Question;
import com.quizapp.demo.question.QuestionRepository;
import com.quizapp.demo.question.QuestionService;
import com.quizapp.demo.questionOption.QuestionOption;
import com.quizapp.demo.questionOption.QuestionOptionService;
import com.quizapp.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private UserService userService;
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;

    public QuizController(UserService userService, QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.userService = userService;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/quiz/{quizId}")
    public String quiz(Model model, @PathVariable Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        List<Question> questions = questionRepository.findByQuiz(quiz);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);

        return "intro";
    }
}
