package com.quizapp.demo.questionAttempt;

import com.quizapp.demo.question.Question;
import com.quizapp.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {
    int countByQuestionAndUser(Question question, User user);
    Optional<QuestionAttempt> findByUserAndQuestion(User user, Question question);
}
