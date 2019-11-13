package com.example.question_bank.web;

import com.example.question_bank.pojo.PropertyValue;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.service.PropertyValueService;
import com.example.question_bank.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    QuestionService questionService;

    @GetMapping("/questions/{qid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("qid") int qid) throws Exception {
        Question question = questionService.get(qid);
        propertyValueService.init(question);
        return propertyValueService.list(question);
    }

    @PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue bean) throws Exception {
        propertyValueService.update(bean);
        return bean;
    }
}
