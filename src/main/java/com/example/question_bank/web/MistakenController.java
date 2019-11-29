package com.example.question_bank.web;

import com.example.question_bank.pojo.Mistaken;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.User;
import com.example.question_bank.service.MistakenService;
import com.example.question_bank.service.QuestionService;
import com.example.question_bank.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MistakenController {
    @Autowired
    MistakenService mistakenService;
    @Autowired
    QuestionService questionService;

    @PostMapping("mistakens")
    public Object add(int qid, HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Mistaken> mistakens = mistakenService.listByUser(user);
        boolean found = false;
        for(Mistaken mistaken : mistakens){
            if(mistaken.getQuestion().getId() == qid){
                found = true;
                break;
            }
        }

        if(!found){
            Mistaken mistaken = new Mistaken();
            mistaken.setQuestion(questionService.get(qid));
            mistaken.setUser((User)session.getAttribute("user"));
            mistakenService.add(mistaken);
        }

        return Result.success();
    }

    @PostMapping("mistakens/list")
    public Object addList(@RequestBody Integer[] wrongQuestion,HttpSession session){
        User user = (User) session.getAttribute("user");
        for (Integer integer : wrongQuestion) {
            Mistaken m = new Mistaken();
            Question question = questionService.get(integer);
            m.setQuestion(question);
            m.setUser(user);
            mistakenService.add(m);
        }
        return Result.success();
    }

    @GetMapping("mistakens")
    public Object collection(HttpSession session){
        User user =(User) session.getAttribute("user");
        List<Mistaken> mistakens = mistakenService.listByUser(user);
        List<Question> questions = new ArrayList<>();
        for(Mistaken mistaken : mistakens){
            Question question = mistaken.getQuestion();
            questions.add(question);
        }
        return questions;
    }

    @DeleteMapping("mistakens/{qid}")
    public Object delete(@PathVariable(name = "qid") int qid, HttpSession session) throws Exception{
        User user = (User)session.getAttribute("user");
        Question question = questionService.get(qid);
        Mistaken m = mistakenService.findbyQuestionAndUser(question,user);
        System.out.println("mistaken:"+m);
        mistakenService.delete(m.getId());
        return null;
    }
}
