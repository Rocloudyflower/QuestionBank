package com.example.question_bank.service;

import com.example.question_bank.dao.HotWordDAO;
import com.example.question_bank.pojo.HotWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotWordService {
    @Autowired
    HotWordDAO hotWordDAO;

    public List<HotWord> sortBySearchtimes(){
        return hotWordDAO.findAllByOrderBySearchtimesDesc();
    }

    public boolean exitByHotWord(String hotword){
        return hotWordDAO.existsById(hotword);
    }

    public void save(HotWord hotWord){
        hotWordDAO.save(hotWord);
    }

    public HotWord get(String hotWord){
        return hotWordDAO.getOne(hotWord);
    }

}
