package com.quizapp.demo.userQuizStatus;

import com.quizapp.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizStatusRepository extends JpaRepository<UserQuizStatus, Long> {
    List<UserQuizStatus> findByUserAndQuizUnit(User user, String unit);

}
