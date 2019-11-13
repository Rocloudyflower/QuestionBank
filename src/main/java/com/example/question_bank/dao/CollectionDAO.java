package com.example.question_bank.dao;

import com.example.question_bank.pojo.Collection;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionDAO extends JpaRepository<Collection, Integer> {

    public List<Collection> findByUserOrderByIdAsc(User user);
    public Collection findByQuestionAndUser(Question question,User user);
}
