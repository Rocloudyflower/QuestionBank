package com.example.question_bank.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ForePageController {
    @GetMapping(value = "/")
    public String index(){
        return "redirect:home";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "fore/login";
    }

    @GetMapping(value = "/register")
    public String register(){ return "fore/register"; }

    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:home";
    }

    @GetMapping(value = "/home")
    public String home(){
        return "fore/home";
    }

    @GetMapping(value="/search")
    public String searchResult(){
        return "fore/search";
    }

    @GetMapping(value = "/collection")
    public String collection(){
        return "fore/collection";
    }

    @GetMapping(value = "/exercise")
    public String Exercise(){
        return "fore/exercise";
    }

    @GetMapping(value = "/mistaken")
    public String Mistaken(){
        return "fore/mistaken";
    }

    @GetMapping(value = "/history")
    public String history(){
        return "fore/history";
    }

    @GetMapping(value = "/rankinglist")
    public String rankinglist(){
        return "fore/rankinglist";
    }

}
