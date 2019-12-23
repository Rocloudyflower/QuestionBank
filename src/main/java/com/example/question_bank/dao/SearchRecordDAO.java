package com.example.question_bank.dao;

import com.example.question_bank.pojo.SearchRecord;
import com.example.question_bank.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRecordDAO extends JpaRepository<SearchRecord, Integer> {
    List<SearchRecord> findByUserOrderByIdAsc(User user);

}
