package com.quizapp.demo.userAttempt;

import com.quizapp.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.quizapp.demo.quiz.Quiz.Difficulty.*;

@Service
public class UserAttemptService {
    @Autowired
    UserAttemptRepository userAttemptRepository;

    public  UserAttempt createUserAttempt(UserAttempt userAttempt){
        return userAttemptRepository.save(userAttempt);
    }

    public void saveUserAttempt(UserAttempt userAttempt){
        userAttemptRepository.save(userAttempt);
    }

    public void checkLevel(User user, int totalPassedQuizzes, int totalScore){
        if (user.getLevel() == INTERMEDIATE && (totalPassedQuizzes >= 3 && totalScore < (80/100)*100)){
            user.setLevel(BEGINNER);
        }
        else if (user.getLevel() == BEGINNER && (totalPassedQuizzes >= 3 && totalScore > (90/100)*100)){
            user.setLevel(INTERMEDIATE);
        }
        else if (user.getLevel() == INTERMEDIATE && (totalPassedQuizzes >= 3 && totalScore > (98/100)*100)){
            user.setLevel(EXPERT);
        }
    }
}
