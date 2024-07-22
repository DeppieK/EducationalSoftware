package com.quizapp.demo.userQuizStatus;

import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.quiz.QuizRepository;
import com.quizapp.demo.user.User;
import com.quizapp.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQuizStatusService {

    @Autowired
    private UserQuizStatusRepository userQuizStatusRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    public List<UserQuizStatus> getUserQuizStatuses(User user, String unit) {
        List<Quiz> quizzes = quizRepository.findByUnit(unit);
        List<UserQuizStatus> statuses = userQuizStatusRepository.findByUserAndQuizUnit(user, unit);

        // Create status entries if they don't exist
        for (Quiz quiz : quizzes) {
            Optional<UserQuizStatus> status = statuses.stream()
                    .filter(s -> s.getQuiz().getQuizId().equals(quiz.getQuizId()))
                    .findFirst();
            if (status.isEmpty()) {
                userQuizStatusRepository.save(new UserQuizStatus(user, quiz, false));
            }
        }

        // Update the list after adding missing entries
        statuses = userQuizStatusRepository.findByUserAndQuizUnit(user, unit);

        // Find the first quiz the user hasn't completed
        boolean enableNext = true;
        for (UserQuizStatus status : statuses) {
            if (enableNext && !status.isEnabled()) {
                status.setEnabled(true);
                userQuizStatusRepository.save(status);
                enableNext = false;
            } else if (!enableNext) {
                status.setEnabled(false);
                userQuizStatusRepository.save(status);
            }
        }

        return statuses;
    }
}