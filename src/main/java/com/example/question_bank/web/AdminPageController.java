package com.example.question_bank.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    @GetMapping(value="/admin")
    public String admin(){
        return "redirect:admin_category_list";
    }

    @GetMapping(value="/admin_data_list")
    public String listData(){
        return "admin/listData";
    }

    @GetMapping(value="/admin_category_list")
    public String listCategory(){
        return "admin/listCategory";
    }

    @GetMapping(value="/admin_category_edit")
    public String editCategory(){
        return "admin/editCategory";
    }

    @GetMapping(value="/admin_unit_list")
    public String listUnit(){
        return "admin/listUnit";
    }

    @GetMapping(value="/admin_unit_edit")
    public String editUnit(){
        return "admin/editUnit";
    }

    @GetMapping(value="/admin_user_list")
    public String listUser(){
        return "admin/listUser";
    }

    @GetMapping(value="/admin_property_list")
    public String listProperty(){
        return "admin/listProperty";
    }

    @GetMapping(value="/admin_property_edit")
    public String editProperty(){
        return "admin/editProperty";
    }

    @GetMapping(value="/admin_question_list")
    public String listQuestion(){
        return "admin/listQuestion";
    }

    @GetMapping(value="/admin_question_edit")
    public String editQuestion(){
        return "admin/editQuestion";
    }

    @GetMapping(value="/admin_propertyValue_edit")
    public String editPropertyValue(){
        return "admin/editPropertyValue";
    }

    @GetMapping(value = "/adminlogin")
    public String login(){
        return "admin/adminLogin";
    }
}
