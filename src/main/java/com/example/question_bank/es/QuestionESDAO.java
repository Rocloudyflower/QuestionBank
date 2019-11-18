package com.example.question_bank.es;

import com.example.question_bank.pojo.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionESDAO extends ElasticsearchRepository<Question,Integer> {
}
