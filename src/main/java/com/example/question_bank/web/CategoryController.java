package com.example.question_bank.web;

import com.example.question_bank.pojo.Category;
import com.example.question_bank.service.CategoryService;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Category> page = categoryService.list(start, size, 6);  //10表示导航分页最多有10个，像 [1,2,3,4,5,6,...] 这样
        return page;
    }

    @PostMapping("/categories")
    public Object add(Category bean) throws Exception {
        categoryService.add(bean);
        return bean;
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id)  throws Exception {
        categoryService.delete(id);
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        return categoryService.get(id);
    }

    @PostMapping("/categories/{id}")
    public Object update(Category bean, HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);

        return bean;
    }

    @GetMapping("/categories/getall")
    public List<Category> getall(){
        return categoryService.list();
    }
}
