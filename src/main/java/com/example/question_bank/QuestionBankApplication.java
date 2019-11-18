package com.example.question_bank;

import com.example.question_bank.util.PortUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.example.question_bank.es")
@EnableJpaRepositories(basePackages = {"com.example.question_bank.dao","com.example.question_bank.pojo"})
public class QuestionBankApplication {

    static {
        PortUtil.checkPort(9300,"ElasticSearch",true);
        PortUtil.checkPort(5601,"Kibana可视化工具",true);
    }

    public static void main(String[] args) {
        SpringApplication.run(QuestionBankApplication.class, args);
    }

}
