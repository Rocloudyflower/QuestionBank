package com.example.question_bank.web;

import com.example.question_bank.pojo.RankingList;
import com.example.question_bank.pojo.User;
import com.example.question_bank.service.RankingListService;
import com.example.question_bank.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RankingListController {
    @Autowired
    RankingListService rankingListService;

    @PostMapping("exercises/result")
    public Object add(@RequestBody RankingList rankingList, HttpSession session){
        User user = (User) session.getAttribute("user");
        rankingList.setUser(user);
        rankingListService.updateList(rankingList);
        return Result.success();
    }

    @GetMapping("rankinglist/{unitid}")
    public List<RankingList> list(@PathVariable(name = "unitid") int unitid){
        return rankingListService.list(unitid);
    }
}
