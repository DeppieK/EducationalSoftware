package com.quizapp.demo.quiz;

import com.quizapp.demo.question.Question;
import com.quizapp.demo.question.QuestionRepository;
import com.quizapp.demo.user.User;
import com.quizapp.demo.user.UserService;
import com.quizapp.demo.userAttempt.UserAttempt;
import com.quizapp.demo.userAttempt.UserAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private UserService userService;
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;
    private UserAttemptRepository userAttemptRepository;
    private UserAttempt userAttempt;

    public QuizController(UserService userService, QuizRepository quizRepository, QuestionRepository questionRepository, UserAttemptRepository userAttemptRepository) {
        this.userService = userService;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.userAttemptRepository = userAttemptRepository;
    }

    @GetMapping("/quiz/{quizId}")
    public String quiz(Model model, @PathVariable Long quizId, Principal principal) {
        //get the current user
        User user = userService.findByUsername(principal.getName());

        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        //find the questions for this quiz
        List<Question> questions = questionRepository.findByQuiz(quiz);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);

        return "quiz";
    }

    @PostMapping("/submitQuiz/{quizId}")
    public String submitQuiz(Model model, @PathVariable Long quizId, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        List<UserAttempt> userAttempts = userAttemptRepository.findByUserAndQuiz(user, quiz);

        //logging user attempts for debugging
        System.out.println("User Attempts: " + userAttempts);

        model.addAttribute("quiz", quiz);
        model.addAttribute("userAttempts", userAttempts);

        return "resultsPage";
    }
}
