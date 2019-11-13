package com.example.question_bank.dao;

import com.example.question_bank.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
