package com.example.question_bank.web;

import com.example.question_bank.pojo.Category;
import com.example.question_bank.pojo.HotWord;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.Unit;
import com.example.question_bank.service.CategoryService;
import com.example.question_bank.service.HotWordService;
import com.example.question_bank.service.QuestionService;
import com.example.question_bank.service.UnitService;
import com.example.question_bank.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HotWordController {
    @Autowired
    HotWordService hotWordService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    QuestionService questionService;
    @Autowired
    UnitService unitService;

    @GetMapping("hotwords")
    public List<HotWord> sortBySearchtimes(){
        int hotwordNum = 0;
        List<HotWord> hotWords = hotWordService.sortBySearchtimes();
        List<HotWord> hotWordList = new ArrayList<>();
        for(HotWord hotWord : hotWords){
            if(hotwordNum > 5){
                break;
            }
            hotWordList.add(hotWord);
            hotwordNum = hotwordNum + 1;
        }
        return hotWordList;
    }

    @GetMapping("/wordcloud")
    public Object wordCloud(){
//        hotWordService.createWordCloud();
//        List<Category> categories = categoryService.list();
//        List<Integer> questionCount = new ArrayList<>();
        List<Unit> units = unitService.list();
        List<Integer> unitCount = new ArrayList<>();
        List<Integer> unitSearch = new ArrayList<>();
        List<HotWord> hotWords = new ArrayList<>();

//        for(Category category : categories){
//            List<Question> questions = questionService.listByCategory(category);
//            questionCount.add(questions.size());
//        }

        for(Unit unit : units){
            List<Question> questions = questionService.listByUnit(unit);
            unitCount.add(questions.size());
            int count = 0;
            for (Question q : questions){
                count += q.getSearched();
            }
            unitSearch.add(count);
        }

        int i =0;
        List<HotWord> hotWordList = hotWordService.sortBySearchtimes();
        for(HotWord hotWord : hotWordList){
            if(i<50){
                hotWords.add(hotWord);
                i++;
            }else break;
        }


        Map<String,Object> map= new HashMap<>();
//        map.put("categories", categories);
//        map.put("questionCount", questionCount);
        map.put("units", units);
        map.put("unitCount", unitCount);
        map.put("unitSearch", unitSearch);
        map.put("hotwords", hotWords);

        return Result.success(map);
    }

}
