package com.example.question_bank.web;

import com.example.question_bank.pojo.Question;
import com.example.question_bank.service.PropertyValueService;
import com.example.question_bank.service.QuestionService;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    PropertyValueService propertyValueService;

    @PostMapping("/questions")
    public Object add(@RequestBody Question bean) throws Exception {
        questionService.add(bean);
        return bean;
    }

    @Transactional
    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable("id") int id)  throws Exception {
        Question question = questionService.get(id);
        propertyValueService.delete(question);
        questionService.delete(id);
        return null;
    }

    @PutMapping("/questions")
    public Object update(@RequestBody Question bean) throws Exception {
        questionService.update(bean);
        return bean;
    }

    @GetMapping("/units/{uid}/questions")
    public Page4Navigator<Question> list(@PathVariable("uid") int uid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Question> page = questionService.list(uid, start, size,10 );

        return page;
    }

    @GetMapping("/questions/{qid}")
    public Question get(@PathVariable("qid") int qid){
        return questionService.get(qid);
    }

    @GetMapping("excercises/{uid}/questionsinfo")
    public List<Question> get4exercise(@PathVariable(name = "uid") int uid) throws Exception{
        return questionService.listexcersice(uid);
    }




}
