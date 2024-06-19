package com.quizapp.demo.userAttempt;


import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAttemptRepository extends JpaRepository<UserAttempt, Long> {
    Optional<UserAttempt> findById(Long id);
    List<UserAttempt> findByUserAndQuiz(User user, Quiz quiz);


}
