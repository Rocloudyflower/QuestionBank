package com.example.question_bank.web;

import com.example.question_bank.pojo.*;
import com.example.question_bank.service.*;
import com.example.question_bank.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static com.example.question_bank.util.Jieba.splitWords;


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

    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session){
        String email =  userParam.getEmail();

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email, userParam.getPassword());
        try {
            subject.login(token);
            User user = userService.getByEmail(email);
            session.setAttribute("user", user);
            return Result.success();
        } catch (AuthenticationException e) {
            String message ="账号密码错误";
            return Result.fail(message);
        }
    }

    @PostMapping("/foreregister")
    public Object register(@RequestBody User user){
        String email = user.getEmail();

        boolean exist = userService.isExist(email);

        if (exist){
            String message = "该邮箱已被注册";
            return Result.fail(message);
        }
        String password = user.getPassword();
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithmName = "md5";

        String encodedPassword = new SimpleHash(algorithmName,password,salt,times).toString();

        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);

        user.setName(name);
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.add(user);

        return Result.success();
    }


    @GetMapping("/forecheckLogin")
    public Object checkLogin() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
            return Result.success();
        else
            return Result.fail("未登录");
    }

    @GetMapping("foresearch")
    public List<Question> search(@RequestParam(name = "keyword") String keyword, HttpServletRequest request) throws IOException {
        if(null == keyword){
            return new ArrayList<>();
        }

        List<Question> questions_ = questionService.searchQuestionDetail(keyword);

        List<String> words = splitWords(keyword.toLowerCase());
        System.out.println(words);

//        遍历热词库，若该热词存在，数据库中searchtimes字段+1
        if (!words.isEmpty()){
            for (String word : words){
                if(hotWordService.exitByHotWord(word)){
                    HotWord hotWord = hotWordService.get(word);
                    int searchtimes = hotWord.getSearchtimes() + 1;
                    hotWord.setSearchtimes(searchtimes);
                    hotWordService.save(hotWord);
                }else {
                    words.remove(word);
                }
            }

            //        遍历题库，计算每道题含有多少个关键词
            List<Question> questions = questionService.getAll();
            for (Question question : questions){
                int count = 0;
                for (String word : words){
                    if (question.getDetailquestion().toLowerCase().contains(word)){
                        count++;
                    }
                }
                question.setContainHotwords(count);
            }
            questions.sort(Question::compareTo);

            for (Question question : questions){
                System.out.println(questions_.size() + " " + questions_.contains(question));
                if (questions_.size() >= 10 || 0 == question.getContainHotwords()) break;
                if (!questions_.contains(question)){
                    questions_.add(question);
                }
            }
        }

        return questions_;
    }

}
