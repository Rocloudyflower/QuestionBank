package com.example.question_bank.web;

import com.example.question_bank.pojo.HotWord;
import com.example.question_bank.service.HotWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HotWordController {
    @Autowired
    HotWordService hotWordService;

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

}
