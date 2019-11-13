package com.example.question_bank.dao;

import com.example.question_bank.pojo.Category;
import com.example.question_bank.pojo.Question;
import com.example.question_bank.pojo.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionDAO extends JpaRepository<Question, Integer> {
    Page<Question> findByCategory(Category category, Pageable pageable);
    List<Question> findByCategoryOrderById(Category category);
    Page<Question> findByUnit(Unit unit, Pageable pageable);
    List<Question> findByUnitOrderById(Unit unit);
//    List<Question> findByDetailquestionLike(String keyword, Pageable pageable);
    Page<Question> findByDetailquestionLike(String keyword, Pageable pageable);
}
