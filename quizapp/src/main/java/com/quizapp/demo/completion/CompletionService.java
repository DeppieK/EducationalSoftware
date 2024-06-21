package com.quizapp.demo.completion;

import com.quizapp.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    @Autowired
    private CompletionRepository completionRepository;

    public int getCompletionCountForUserAndQuiz(Long userId, Long quizId) {
        return completionRepository.findCompletionCountForUserAndQuiz(userId, quizId);
    }

}
