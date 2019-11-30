package com.example.question_bank.dao;

import com.example.question_bank.pojo.HotWord;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotWordDAO extends JpaRepository<HotWord,String> {
    List<HotWord> findAllByOrderBySearchtimesDesc();
}
