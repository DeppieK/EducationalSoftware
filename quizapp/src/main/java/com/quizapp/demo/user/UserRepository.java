package com.quizapp.demo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Logger logger = LoggerFactory.getLogger(UserRepository.class);

    default User findByUsernameWithLogging(String username) {
        logger.info("Searching for user with username: {}", username);
        User user = findByUsername(username);
        if (user == null) {
            logger.warn("User not found with username: {}", username);
        } else {
            logger.info("User found: {}", user.getUsername());
        }
        return user;
    }
}

