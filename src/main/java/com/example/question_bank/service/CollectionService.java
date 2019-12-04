package com.example.question_bank.service;

import com.example.question_bank.dao.CollectionDAO;
import com.example.question_bank.pojo.Collection;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.User;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionDAO collectionDAO;

    public void add(Collection collection){
        collectionDAO.save(collection);
    }
    public Collection get(int id){
        return collectionDAO.getOne(id);
    }
    public void delete(int id){
        collectionDAO.deleteById(id);
    }
    public void update(Collection collection){
        collectionDAO.save(collection);
    }
    public List<Collection> listByUser(User user){
        return collectionDAO.findByUserOrderByIdAsc(user);
    }
    public Collection findbyQuestionAndUser(Question question,User user){
        return collectionDAO.findByQuestionAndUser(question,user);
    }

}
