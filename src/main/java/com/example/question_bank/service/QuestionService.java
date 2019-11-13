package com.example.question_bank.service;

import com.example.question_bank.dao.CategoryDAO;
import com.example.question_bank.dao.QuestionDAO;
import com.example.question_bank.pojo.*;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    UnitService unitService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    PropertyValueService propertyValueService;

    public void add(Question bean) {
        questionDAO.save(bean);
    }

    public void delete(int id) {
        questionDAO.delete(id);
    }

    public Question get(int id) {
        return questionDAO.findOne(id);
    }

    public void update(Question bean) {
        questionDAO.save(bean);
    }

    public Page4Navigator<Question> list(int uid, int start, int size, int navigatePages){
        Unit unit = unitService.get(uid);
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Question> pageFromJPA = questionDAO.findByUnit(unit,pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

//    public List<Question> search(String keyword, int start, int size) {
//        Sort sort = new Sort(Sort.Direction.ASC, "id");
//        Pageable pageable = new PageRequest(start, size, sort);
//        return questionDAO.findByDetailquestionLike("%"+keyword+"%",pageable);
//    }

    public Page4Navigator<Question> search(String keyword, int start, int size, int navigatePages){
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Question> pageFromJPA = questionDAO.findByDetailquestionLike("%"+keyword+"%",pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

//    刷题模式
    public List<Question> listexcersice(int uid) {
        Unit unit = unitService.get(uid);
//        Sort sort = new Sort(Sort.Direction.ASC,"id");

        List<Question> questions = questionDAO.findByUnitOrderById(unit);
        return questions;
    }
}
