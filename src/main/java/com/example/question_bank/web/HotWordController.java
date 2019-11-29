package com.example.question_bank.web;

import com.example.question_bank.pojo.HotWord;
import com.example.question_bank.service.HotWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotWordController {
    @Autowired
    HotWordService hotWordService;

    @GetMapping("hotwords")
    public List<HotWord> sortBySearchtimes(){
        int hotwordNum = 6;
        PageRequest pageRequest = new PageRequest(0,hotwordNum);
        return hotWordService.sortBySearchtimes(pageRequest);
    }

}
