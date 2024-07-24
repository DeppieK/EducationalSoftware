package com.quizapp.demo.question;

import com.quizapp.demo.quiz.Quiz;
import com.quizapp.demo.user.User;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findById(Long id);
    List<Question> findByQuiz(Quiz quiz);

    @Query("SELECT qa.question, COUNT(qa) FROM QuestionAttempt qa WHERE qa.user = :user AND qa.question.quiz = :quiz GROUP BY qa.question")
    List<Object[]> findAttemptsByUserAndQuiz(@Param("user") User user, @Param("quiz") Quiz quiz);


}
