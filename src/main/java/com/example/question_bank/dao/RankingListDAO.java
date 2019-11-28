package com.example.question_bank.dao;

import com.example.question_bank.pojo.RankingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingListDAO extends JpaRepository<RankingList,Integer> {
}
