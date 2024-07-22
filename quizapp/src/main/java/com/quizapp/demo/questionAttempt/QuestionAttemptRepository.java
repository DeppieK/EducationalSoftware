package com.quizapp.demo.questionAttempt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {
}
