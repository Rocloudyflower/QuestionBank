package com.example.question_bank.web;

import com.example.question_bank.dao.UnitDAO;
import com.example.question_bank.pojo.Unit;
import com.example.question_bank.service.UnitService;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UnitController {
    @Autowired
    UnitService unitService;

    @GetMapping("/units")
    public Page4Navigator<Unit> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Unit> page = unitService.list(start, size, 6);  //10表示导航分页最多有10个，像 [1,2,3,4,5,6,...] 这样
        return page;
    }

    @PostMapping("/units")
    public Object add(Unit bean, HttpServletRequest request) throws Exception {
        unitService.add(bean);
        return bean;
    }

    @DeleteMapping("/units/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        unitService.delete(id);
        return null;
    }

    @GetMapping("/units/{id}")
    public Unit get(@PathVariable("id") int id) throws Exception {
        return unitService.get(id);
    }

    @PostMapping("/units/{id}")
    public Object update(Unit bean, HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        bean.setName(name);
        unitService.update(bean);
        return bean;
    }

    @GetMapping("/units/getall")
    public List<Unit> getall(){
        return unitService.list();
    }

}
