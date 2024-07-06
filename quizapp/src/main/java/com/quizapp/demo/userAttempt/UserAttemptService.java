package com.quizapp.demo.userAttempt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
