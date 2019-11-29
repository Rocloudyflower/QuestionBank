package com.example.question_bank.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hotwords")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class HotWord {
    @Id
    private String hotword;

    private int searchtimes;

    public String getHotword() {
        return hotword;
    }

    public void setHotword(String hotword) {
        this.hotword = hotword;
    }

    public int getSearchtimes() {
        return searchtimes;
    }

    public void setSearchtimes(int searchtimes) {
        this.searchtimes = searchtimes;
    }
}
