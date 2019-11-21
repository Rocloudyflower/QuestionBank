package com.example.question_bank.web;

import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.SearchRecord;
import com.example.question_bank.pojo.User;
import com.example.question_bank.service.QuestionService;
import com.example.question_bank.service.SearchRecordService;
import com.example.question_bank.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class SearchRecordController {
    @Autowired
    SearchRecordService searchRecordService;
    @Autowired
    QuestionService questionService;

    @GetMapping("/histories")
    public Object collection(HttpSession session){
        User user =(User) session.getAttribute("user");
        List<SearchRecord> searchRecords = searchRecordService.listByUser(user);
        List<Question> questions = new ArrayList<>();
        for(SearchRecord searchRecord : searchRecords){
            Question question = searchRecord.getQuestion();
            questions.add(question);
        }
        return questions;
    }

}
