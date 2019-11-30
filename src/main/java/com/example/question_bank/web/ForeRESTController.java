package com.example.question_bank.web;

import com.example.question_bank.pojo.*;
import com.example.question_bank.service.*;
import com.example.question_bank.util.Result;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ForeRESTController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    CollectionService collectionService;
    @Autowired
    HotWordService hotWordService;

    @PostMapping("forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String email = userParam.getEmail();

        User user = userService.get(email, userParam.getPassword());
        if (null == user) {
            String message = "账号密码错误";
            return Result.fail(message);
        } else {
            session.setAttribute("user", user);
            return Result.success();
        }
    }

    @PostMapping("foreregister")
    public Object register(@RequestBody User user){
        String email = user.getEmail();
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);

        boolean exist = userService.isExist(email);

        if (exist){
            String message = "该邮箱已被注册";
            return Result.fail(message);
        }
        user.setName(name);
        userService.add(user);
        return Result.success();
    }

    @GetMapping("forecheckLogin")
    public Object checkLogin(HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null != user)
            return Result.success();
        return Result.fail("未登录");
    }

    @PostMapping("foresearch")
    public List<Question> search(@RequestParam(name = "keyword") String keyword){
        if(null == keyword)
            keyword = "";

        System.out.println("keyword: " + keyword);
//        关键热词+1
//        JiebaSegmenter segmenter = new JiebaSegmenter();
//        List<String> words = segmenter.sentenceProcess(keyword);

//        遍历热词库，若该热词存在，数据库中searchtimes字段+1
//        for (String word : words){
//            if(hotWordService.exitByHotWord(word)){
//                HotWord hotWord = hotWordService.get(word);
//                int searchtimes = hotWord.getSearchtimes() + 1;
//                hotWord.setSearchtimes(searchtimes);
//                hotWordService.save(hotWord);
//            }
//        }
        System.out.println(questionService.searchQuestionDetail(keyword));
        return questionService.searchQuestionDetail(keyword);
    }

}
