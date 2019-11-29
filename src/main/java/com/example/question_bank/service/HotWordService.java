package com.example.question_bank.service;

import com.example.question_bank.dao.HotWordDAO;
import com.example.question_bank.pojo.HotWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class HotWordService {
    @Autowired
    HotWordDAO hotWordDAO;

    public List<HotWord> sortBySearchtimes(PageRequest pageRequest){
        return hotWordDAO.findByOrderBySearchtimes(pageRequest);
    }

    public boolean exitByHotWord(String hotword){
        return hotWordDAO.exists(hotword);
    }

    public void save(HotWord hotWord){
        hotWordDAO.save(hotWord);
    }

    public HotWord get(String hotWord){
        return hotWordDAO.getOne(hotWord);
    }
}
