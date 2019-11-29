package com.example.question_bank.service;

import com.example.question_bank.dao.CategoryDAO;
import com.example.question_bank.dao.QuestionDAO;
import com.example.question_bank.es.QuestionESDAO;
import com.example.question_bank.pojo.*;
import com.example.question_bank.util.Page4Navigator;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    UnitService unitService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    PropertyValueService propertyValueService;
//    @Autowired
//    QuestionESDAO questionESDAO;

    public void add(Question bean) {
        questionDAO.save(bean);
//        questionESDAO.save(bean);
    }

    public void delete(int id) {
        questionDAO.delete(id);
//        questionESDAO.delete(id);
    }

    public Question get(int id) {
        return questionDAO.findOne(id);
    }

    public void update(Question bean) {
        questionDAO.save(bean);
//        questionESDAO.save(bean);
    }

    public Page4Navigator<Question> list(int uid, int start, int size, int navigatePages){
        Unit unit = unitService.get(uid);
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Question> pageFromJPA = questionDAO.findByUnit(unit,pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public Page4Navigator<Question> search(String keyword, int start, int size, int navigatePages){
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Question> pageFromJPA = questionDAO.findByDetailquestionLike(keyword,pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    //    刷题模式
    public List<Question> listexcersice(int uid) {
        Unit unit = unitService.get(uid);

        List<Question> questions = questionDAO.findByUnitOrderById(unit);

        int exerciseNum = Math.min(questions.size(), 10);

        //随机题库
        Collections.shuffle(questions);
        List<Question> exercices = new ArrayList<>();
        int i = 0;
        while (i < exerciseNum){
            exercices.add(questions.get(i));
            i++;
        }

        return exercices;
    }

//    public List<Question> search(String keyword) {
////        initDatabase2ES();
//        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
//                .add(QueryBuilders.matchQuery("detailquestion", keyword),
//                        ScoreFunctionBuilders.weightFactorFunction(1000))
//                .add(QueryBuilders.matchQuery("explanation", keyword),
//                        ScoreFunctionBuilders.weightFactorFunction(100));
//
//        Pageable pageable = new PageRequest(0, 10);
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(functionScoreQueryBuilder)
//                .withPageable(pageable)
//                .withSort(SortBuilders.scoreSort()).build();
//
////        SearchRequestBuilder searchRequestBuilder = new SearchRequestBuilder();
////        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
////        //分页
////        searchRequestBuilder.setFrom(0).setSize(10);
////        //explain为true表示根据数据相关度排序，和关键字匹配最高的排在前面
////        searchRequestBuilder.setExplain(true);
////        searchRequestBuilder.setQuery(functionScoreQueryBuilder);
//        Page<Question> page = questionESDAO.search(searchQuery);
//        return page.getContent();
//    }

//    private void initDatabase2ES() {
//
////        清空ES数据库
//        questionESDAO.deleteAll();
//
//        Pageable pageable = new PageRequest(0, 5);
//        Page<Question> page = questionESDAO.findAll(pageable);
//        if(page.getContent().isEmpty()) {
//            List<Question> questions= questionDAO.findAll();
//            questionESDAO.save(questions);
//        }
//    }

    //用户评分
    public void questionsave(int id, double userScore){ // id为用户评分的题目的id，userScore为用户的评分
        Question currentQuestion = get(id);
        int totalTimes = currentQuestion.getEvatimes();
        double currentScore = currentQuestion.getScore();
        double avgScore = ( currentScore * totalTimes + userScore )/( totalTimes + 1 );
        currentQuestion.setEvatimes( totalTimes + 1 );
        currentQuestion.setScore(avgScore);
        update(currentQuestion);
    }

    //    关键词相关 -- 按xx排序，返回前10个
    public List<Question> searchQuestionDetail(String keystring)
    {
        return questionDAO.findByDetailquestionLike(keystring);
    }

}
