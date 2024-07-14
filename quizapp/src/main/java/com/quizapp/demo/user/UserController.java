package com.quizapp.demo.user;

import com.quizapp.demo.completion.Completion;
import com.quizapp.demo.question.Question;
import com.quizapp.demo.question.QuestionRepository;
import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.quiz.QuizRepository;
import com.quizapp.demo.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    private QuizService quizService;
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;
    @Autowired
    private User user;

    public UserController(UserService userService,QuizRepository quizRepository, QuestionRepository questionRepository, QuizService quizService) {
        this.userService = userService;
        this.quizService = quizService;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/login")
    public String login() {

        return "sign_in";
    }

    @GetMapping("/signup")
    public String signup() {
        return "registration";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmpassword,
                           @RequestParam String firstname,
                           @RequestParam String lastname,
                           @RequestParam String email,
                           RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmpassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/signup";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setDateOfRegistration(LocalDateTime.now());

        userService.save(user);

        return "redirect:/login";
    }

    @GetMapping("/main")
    public String main(Model model) {

        return "main";
    }

    @GetMapping("/homepage")
    public String homepage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        int completionCount = userService.getCompletionCountForQuizId(user, 3L);

        if (completionCount > 0) {
            return "homepage";
        } else {
            Quiz quiz = quizRepository.findById(3L).orElse(null);

            //find the questions for this quiz
            List<Question> questions = questionRepository.findByQuiz(quiz);

            model.addAttribute("quiz", quiz);
            model.addAttribute("questions", questions);
            model.addAttribute("user", user);

            return "levelsQuiz";
        }
    }

    @PostMapping("/homepage")
    public String submitQuiz(@RequestParam("answers") List<Boolean> answers, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        int mistakes = 0;

        for (Boolean answer : answers) {
            if (!answer) {
                mistakes++;
            }
        }

        if (mistakes < 3) {
            user.setLevel(Quiz.Difficulty.INTERMEDIATE);
        } else {
            user.setLevel(Quiz.Difficulty.BEGINNER);
        }

        userService.save(user);

        // save completion record
        Quiz quiz = quizRepository.findById(3L).orElse(null);
        Completion completion = new Completion(user, quiz, 1);
        userService.saveCompletion(completion);

        return "homepage";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/intro")
    public String intro(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        Quiz.Difficulty userLevel = user.getLevel();

        List<Quiz> quiz = quizService.findByUnitAndDifficulty("Intro", userLevel);

        //sort quizzes based on the label
        quiz.sort(Comparator.comparing(Quiz::getLabel));

        model.addAttribute("quiz", quiz);

        return "quizList";
    }

    @GetMapping("/basics")
    public String basics(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        Quiz.Difficulty userLevel = user.getLevel();

        List<Quiz> quiz = quizService.findByUnitAndDifficulty("Basics", userLevel);

        quiz.sort(Comparator.comparing(Quiz::getLabel));

        model.addAttribute("quiz", quiz);

        return "quizList";
    }

    @GetMapping("/forms")
    public String forms(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        Quiz.Difficulty userLevel = user.getLevel();

        List<Quiz> quiz = quizService.findByUnitAndDifficulty("Forms", userLevel);

        quiz.sort(Comparator.comparing(Quiz::getLabel));

        model.addAttribute("quiz", quiz);

        return "quizList";
    }

    @GetMapping("/glossary")
    public String glossary(Model model) {
        return "glossary";
    }
}