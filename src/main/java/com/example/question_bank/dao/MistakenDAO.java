package com.example.question_bank.dao;

import com.example.question_bank.pojo.Mistaken;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MistakenDAO extends JpaRepository<Mistaken,Integer> {
    Mistaken findByQuestionAndUser(Question question, User user);
    List<Mistaken> findByUserOrderByIdDesc(User user);
}
