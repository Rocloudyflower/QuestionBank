package com.example.question_bank.dao;

import com.example.question_bank.pojo.RankingList;
import com.example.question_bank.pojo.Unit;
import com.example.question_bank.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingListDAO extends JpaRepository<RankingList,Integer> {
    List<RankingList> findByUnitOrderByScoreDesc(Unit unit);
    RankingList findByUserAndUnit(User user, Unit unit);
}
