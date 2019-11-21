package com.example.question_bank.service;

import com.example.question_bank.dao.SearchRecordDAO;
import com.example.question_bank.pojo.SearchRecord;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.SearchRecord;
import com.example.question_bank.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SearchRecordService {

    @Autowired
    SearchRecordDAO searchRecordDAO;

    public void add(SearchRecord searchRecord){
        searchRecordDAO.save(searchRecord);
    }
    public SearchRecord get(int id){
        return searchRecordDAO.findOne(id);
    }
    public void delete(int id){
        searchRecordDAO.delete(id);
    }
    public void update(SearchRecord searchRecord){
        searchRecordDAO.save(searchRecord);
    }
    public List<SearchRecord> listByUser(User user){
        return searchRecordDAO.findByUserOrderByIdAsc(user);
    }
}
