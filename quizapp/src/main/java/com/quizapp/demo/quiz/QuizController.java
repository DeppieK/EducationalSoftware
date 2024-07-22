package com.quizapp.demo.quiz;

import com.quizapp.demo.completion.Completion;
import com.quizapp.demo.completion.CompletionRepository;
import com.quizapp.demo.question.Question;
import com.quizapp.demo.question.QuestionRepository;
import com.quizapp.demo.questionAttempt.QuestionAttempt;
import com.quizapp.demo.questionAttempt.QuestionAttemptRepository;
import com.quizapp.demo.user.User;
import com.quizapp.demo.user.UserService;
import com.quizapp.demo.userAttempt.UserAttempt;
import com.quizapp.demo.userAttempt.UserAttemptRepository;
import com.quizapp.demo.userAttempt.UserAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.quizapp.demo.quiz.Quiz.Difficulty.*;

@Controller
public class QuizController {

    @Autowired
    private UserService userService;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionAttemptRepository questionAttemptRepository;
    private final UserAttemptRepository userAttemptRepository;
    private final UserAttemptService userAttemptService;
    private UserAttempt userAttempt;

    @Autowired
    private CompletionRepository completionRepository;

    public QuizController(UserService userService, QuizRepository quizRepository, QuestionRepository questionRepository, QuestionAttemptRepository questionAttemptRepository, UserAttemptRepository userAttemptRepository, UserAttemptService userAttemptService) {
        this.userService = userService;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.questionAttemptRepository = questionAttemptRepository;
        this.userAttemptRepository = userAttemptRepository;
        this.userAttemptService = userAttemptService;
    }

    @GetMapping("/quiz/{quizId}")
    public String quiz(Model model, @PathVariable Long quizId, Principal principal) {
        //get the current user
        User user = userService.findByUsername(principal.getName());
        Long userId = user.getId();

        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        //find the questions for this quiz
        List<Question> questions = questionRepository.findByQuiz(quiz);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        model.addAttribute("userId", userId);
        model.addAttribute("quizId", quizId);

        return "quiz";
    }

    @PostMapping("/submitQuiz/{quizId}")
    public String submitQuiz(Model model, @PathVariable Long quizId,@RequestParam("answers") List<Boolean> answers, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        int score = 0;
        int mistakes = 0;

        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        List<UserAttempt> userAttempts = userAttemptRepository.findByUserAndQuiz(user, quiz);
        List<Question> questions = questionRepository.findByQuiz(quiz);

        for (Boolean answer : answers) {
            if (answer != null && answer) {
                score += 10;
            }
        }

        for (int i = 0; i < questions.size(); i++) {
            Boolean answer = answers.get(i);
            Question question = questions.get(i);
            boolean isCorrect = answer != null && answer;

            if (!isCorrect) {
                //log only incorrect question attempts
                QuestionAttempt questionAttempt = new QuestionAttempt();
                questionAttempt.setAttemptId(user.getId().intValue());
                questionAttempt.setQuestion(question);
                questionAttempt.setIsCorrect(false);
                questionAttemptRepository.save(questionAttempt);

            }
        }

        Completion existingCompletion = completionRepository.findByUserAndQuiz(user,quiz);

        //if completion for this quiz exists then increment the completion
        if (existingCompletion != null){
            existingCompletion.setCompleted(existingCompletion.getCompleted() + 1);
            //save completion record
            userService.saveCompletion(existingCompletion);
        }
        else{
            //else new completion record
            Completion completion = new Completion(user, quiz, 1);
            userService.saveCompletion(completion);
        }

        Integer betterAttempt = userAttemptRepository.findBetterAttempt(user.getId(), quiz.getQuizId());
        int betterAttemptScore = (betterAttempt != null) ? betterAttempt : 0;
        int totalScore = user.getTotalScore() + betterAttemptScore;

        int totalPassedQuizzes = userAttemptRepository.countDistinctPassedQuizzes(user.getId());
        System.out.println(totalPassedQuizzes);

        if ((user.getLevel() == INTERMEDIATE) && (totalPassedQuizzes >= 3) && (totalScore < (80/100)*210)){
            user.setLevel(BEGINNER);
        }
        else if ((user.getLevel() == BEGINNER && (totalPassedQuizzes >= 3) && totalScore > (90/100)*210)){
            user.setLevel(INTERMEDIATE);
        }
        else if ((user.getLevel() == INTERMEDIATE && (totalPassedQuizzes >= 5) && totalScore > (98/100)*210)){
            user.setLevel(EXPERT);
        }

        user.setTotalScore(totalScore);

        UserAttempt userAttempt = new UserAttempt();
        userAttempt.setUser(user);
        userAttempt.setQuiz(quiz);
        userAttempt.setScore(score);
        userAttempt.setStatus(score > 40 ? UserAttempt.Status.PASSED : UserAttempt.Status.FAILED);
        userAttempt.setStartTime(LocalDateTime.now().minusMinutes(5));
        userAttempt.setEndTime(LocalDateTime.now());

        userAttemptService.createUserAttempt(userAttempt);
        //logging user attempts for debugging
        System.out.println("User Attempts: " + userAttempts);

        model.addAttribute("quiz", quiz);
        model.addAttribute("userAttempts", userAttempts);

        return "redirect:/results/" + quizId;
    }
    @GetMapping("/results/{quizId}")
    public String getResultsPage(Model model, @PathVariable Long quizId, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        Long userAttemptId = userAttemptRepository.findMaxAttemptId(user.getId(), quizId);

        UserAttempt userAttempt = userAttemptRepository.findById(userAttemptId).orElse(null); //get the first element which has the highest attemptId
        System.out.println("Latest User Attempt: " + userAttempt);

        model.addAttribute("quiz", quiz);
        model.addAttribute("userAttempt", userAttempt);

        return "resultsPage";
    }
}
