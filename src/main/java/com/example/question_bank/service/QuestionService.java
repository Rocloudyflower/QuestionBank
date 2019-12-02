package com.example.question_bank.service;

import com.example.question_bank.dao.QuestionDAO;
import com.example.question_bank.pojo.*;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<Question> getAll(){
        return questionDAO.findAll();
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


    //    刷题模式
    public List<Question> listexcersice(int uid) {
        Unit unit = unitService.get(uid);

        List<Question> questions = questionDAO.findByUnitOrderById(unit);

        int exerciseNum = Math.min(questions.size(), 10);

        //随机题库
        Collections.shuffle(questions);
        List<Question> exercices = new ArrayList<>();
        int i = 0;
        while (i < exerciseNum){
            exercices.add(questions.get(i));
            i++;
        }

        return exercices;
    }


    //用户评分
    public void questionsave(int id, double userScore){ // id为用户评分的题目的id，userScore为用户的评分
        Question currentQuestion = get(id);
        int totalTimes = currentQuestion.getEvatimes();
        double currentScore = currentQuestion.getScore();
        double avgScore = ( currentScore * totalTimes + userScore )/( totalTimes + 1 );
        currentQuestion.setEvatimes( totalTimes + 1 );
        currentQuestion.setScore(avgScore);
        update(currentQuestion);
    }

    //    关键词相关 -- 按xx排序，返回前10个
    public List<Question> searchQuestionDetail(String keystring)
    {
        return questionDAO.findByDetailquestionContaining(keystring);
    }

    public List<Question> listByCategory(Category category) {
        return questionDAO.findByCategoryOrderById(category);
    }

    public List<Question> listByUnit(Unit unit) {
        return questionDAO.findByUnitOrderById(unit);
    }
}
