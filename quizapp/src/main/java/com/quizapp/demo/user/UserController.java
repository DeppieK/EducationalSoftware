package com.quizapp.demo.user;

import com.quizapp.demo.quiz.Quiz;
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
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    private QuizService quizService;

    public UserController(UserService userService, QuizService quizService) {
        this.userService = userService;
        this.quizService = quizService;
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

        return "redirect:/sign_in";
    }

    @GetMapping("/main")
    public String main(Model model) {

        return "main";
    }

    @GetMapping("/homepage")
    public String homepage(Model model) {
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

    @GetMapping("/quizList")
    public String intro(Model model) {
        List<Quiz> quiz = quizService.getQuiz();
        model.addAttribute("quiz", quiz);

        return "quizList";
    }

    @GetMapping("/basics")
    public String basics(Model model) {
        return "basics";
    }

    @GetMapping("/levelstest")
    public String levelstast(Model model) {
        return "levels";
    }
    @GetMapping("/forms")
    public String forms(Model model) {
        return "forms";
    }

    @GetMapping("/glossary")
    public String glossary(Model model) {
        return "glossary";
    }
}