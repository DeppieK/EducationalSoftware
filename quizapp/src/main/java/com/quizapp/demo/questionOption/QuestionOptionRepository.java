package com.quizapp.demo.questionOption;

import com.quizapp.demo.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {

    Optional<QuestionOption> findById(Long id);

}
