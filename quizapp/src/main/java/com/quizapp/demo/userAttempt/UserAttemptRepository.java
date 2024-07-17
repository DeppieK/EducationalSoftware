package com.quizapp.demo.userAttempt;


import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAttemptRepository extends CrudRepository<UserAttempt, Long> {
    Optional<UserAttempt> findById(Long id);
    List<UserAttempt> findByUserAndQuiz(User user, Quiz quiz);
    List<UserAttempt> findByUser(User user);

    @Query("SELECT MAX(ua.attemptId) FROM UserAttempt ua WHERE ua.user.id = :userId AND ua.quiz.quizId = :quizId")
    Long findMaxAttemptId(@Param("userId") Long userId, @Param("quizId") Long quizId);

    @Query("SELECT MAX(ua.score) FROM UserAttempt ua WHERE ua.user.id = :userId AND ua.quiz.quizId = :quizId")
    Integer findBetterAttempt(@Param("userId") Long userId, @Param("quizId") Long quizId);

    //@Query("SELECT COUNT(DISTINCT ua.quiz) FROM UserAttempt ua WHERE ua.user.id = :userId AND ua.status = UserAttempt.Status.PASSED")
    //int countDistinctPassedQuizzes(@Param("userId") Long userId);


}
