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
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
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

    @GetMapping("wordcloud")
    public Object wordCloud() throws FileNotFoundException {
        hotWordService.createWordCloud();
        List<Category> categories = categoryService.list();
        List<Integer> questionCount = new ArrayList<>();
        List<Unit> units = unitService.list();
        List<Integer> unitCount = new ArrayList<>();

        for(Category category : categories){
            List<Question> questions = questionService.listByCategory(category);
            questionCount.add(questions.size());
        }

        for(Unit unit : units){
            List<Question> questions = questionService.listByUnit(unit);
            unitCount.add(questions.size());
        }

        Map<String,Object> map= new HashMap<>();
        map.put("categories", categories);
        map.put("questionCount", questionCount);
        map.put("units", units);
        map.put("unitCount", unitCount);

        return Result.success(map);
    }

}
