package com.example.question_bank.service;

import com.example.question_bank.dao.RankingListDAO;
import com.example.question_bank.dao.UnitDAO;
import com.example.question_bank.dao.UserDAO;
import com.example.question_bank.pojo.RankingList;
import com.example.question_bank.pojo.Unit;
import com.example.question_bank.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingListService {
    @Autowired
    RankingListDAO rankingListDAO;
    @Autowired
    UnitDAO unitDAO;
    @Autowired
    UserDAO userDAO;

    public void add(RankingList bean) {rankingListDAO.save(bean);}

    public void delete(int id) {rankingListDAO.delete(id);}

    public void update(RankingList bean) {rankingListDAO.save(bean);}

    public RankingList get(int id) {return rankingListDAO.findOne(id);}

    //获取每个章节的排行榜
    //unitid 为目标章节id
    public List<RankingList> list(int unitid){
        Unit unit = unitDAO.getOne(unitid);
        return rankingListDAO.findByUnitOrderByScoreDesc(unit);
    }

    //更新排行榜函数
    //useremail为当前用户email，unitid为当前章节id，score为该次刷题得分
    public void updateList(RankingList rankingList){
        User user = rankingList.getUser();
        Unit unit = rankingList.getUnit();
        int score = rankingList.getScore();
        RankingList rankingList_ = rankingListDAO.findByUserAndUnit(user, unit);
        //ranking表中没有历史记录视为第一次刷题，添加表项
        if(null == rankingList_){
            rankingList.setTimes(1);
            rankingListDAO.save(rankingList);
        }
        //更新排行榜数据
        else {
            if(rankingList_.getScore() < score){
                rankingList_.setScore(score);
            }
            int t = rankingList_.getTimes();
            rankingList_.setTimes(t + 1);
            rankingListDAO.save(rankingList_);
        }

    }
}
