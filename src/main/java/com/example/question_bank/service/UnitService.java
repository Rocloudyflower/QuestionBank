package com.example.question_bank.service;

import com.example.question_bank.dao.UnitDAO;
import com.example.question_bank.pojo.Unit;
import com.example.question_bank.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    @Autowired
    UnitDAO unitDAO;

    public void add(Unit bean) {
        unitDAO.save(bean);
    }

    public void delete(int id) {
        unitDAO.deleteById(id);
    }

    public Unit get(int id) {
        return unitDAO.getOne(id);
    }

    public void update(Unit bean) {
        unitDAO.save(bean);
    }

    public Page4Navigator<Unit> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = unitDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public List<Unit> list() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return unitDAO.findAll(sort);
    }

}
