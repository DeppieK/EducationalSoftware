package com.quizapp.demo.user;

import com.quizapp.demo.completion.CompletionService;
import com.quizapp.demo.quiz.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompletionService completionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public int getCompletionCountForQuizId(User user, Long quizId) {
        // Use CompletionService to get the completion count
        return completionService.getCompletionCountForUserAndQuiz(user.getId(), quizId);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
