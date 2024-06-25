package com.quizapp.demo.completion;

import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompletionRepository extends JpaRepository<Completion, Long> {

    @Query("SELECT COUNT(c) FROM Completion c WHERE c.user.id = :userId AND c.quiz.quizId = :quizId")
    int findCompletionCountForUserAndQuiz(@Param("userId") Long userId, @Param("quizId") Long quizId);

    Completion findByUserAndQuiz(User user, Quiz quiz);

}
