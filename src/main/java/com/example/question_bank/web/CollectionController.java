package com.example.question_bank.web;

import com.example.question_bank.dao.QuestionDAO;
import com.example.question_bank.pojo.Collection;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.User;
import com.example.question_bank.service.CollectionService;
import com.example.question_bank.service.QuestionService;
import com.example.question_bank.util.Page4Navigator;
import com.example.question_bank.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.management.Query;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CollectionController {
    @Autowired
    CollectionService collectionService;
    @Autowired
    QuestionService questionService;


    @GetMapping("collections")
    public Object collection(HttpSession session){
        User user =(User) session.getAttribute("user");
        List<Collection> collections = collectionService.listByUser(user);
        List<Question> questions = new ArrayList<>();
        for(Collection collection : collections){
            Question question = collection.getQuestion();
            questions.add(question);
        }
        return questions;
    }

    @PostMapping("collections")
    public Object add(int qid, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        List<Collection> collections = collectionService.listByUser(user);
        boolean found = false;
        for(Collection collection : collections){
            if(collection.getQuestion().getId() == qid){
                found = true;
                break;
            }
        }
        if(!found){
            Collection collection = new Collection();
            Question question = questionService.get(qid);
            collection.setUser(user);
            collection.setQuestion(question);
            collectionService.add(collection);
        }

        return Result.success();
    }

    @DeleteMapping("collections/{qid}")
    public Object delete(@PathVariable(name = "qid") int qid, HttpSession session) throws Exception{
        User user = (User)session.getAttribute("user");
        Question question = questionService.get(qid);
        Collection c = collectionService.findbyQuestionAndUser(question,user);
        System.out.println("collection:"+c);
        collectionService.delete(c.getId());
        return null;
    }

}
