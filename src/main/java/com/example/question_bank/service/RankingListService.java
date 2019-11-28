package com.example.question_bank.service;

import com.example.question_bank.dao.RankingListDAO;
import com.example.question_bank.pojo.RankingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankingListService {
    @Autowired
    RankingListDAO rankingListDAO;

    public void add(RankingList rankingList){
        rankingListDAO.save(rankingList);
    }



}
