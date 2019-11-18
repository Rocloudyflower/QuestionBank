package com.example.question_bank.dao;

import com.example.question_bank.pojo.Property;
import com.example.question_bank.pojo.PropertyValue;
import com.example.question_bank.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyValueDAO extends JpaRepository<PropertyValue, Integer> {

	List<PropertyValue> findByQuestionOrderByIdAsc(Question question);
	PropertyValue getByPropertyAndQuestion (Property property, Question question);
	void deleteByQuestion(Question question);

}

