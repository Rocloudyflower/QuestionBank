package com.example.question_bank.service;

import com.example.question_bank.dao.MistakenDAO;
import com.example.question_bank.pojo.Mistaken;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class MistakenService {
    @Autowired
    MistakenDAO mistakenDAO;

    public void add(Mistaken mistaken){
        mistakenDAO.save(mistaken);
    }
    public Mistaken get(int id){
        return mistakenDAO.findOne(id);
    }
    public void delete(int id){
        mistakenDAO.delete(id);
    }
    public List<Mistaken> listByUser(User user){
        return mistakenDAO.findByUser(user);
    }
    public Mistaken findbyQuestionAndUser(Question question, User user){
        return mistakenDAO.findByQuestionAndUser(question,user);
    }

}
