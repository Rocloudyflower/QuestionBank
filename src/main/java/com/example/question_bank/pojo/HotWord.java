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
    private String Id;

    private int searchtimes;

    public String getHotword() {
        return Id;
    }

    public void setHotword(String Id) {
        this.Id = Id;
    }

    public int getSearchtimes() {
        return searchtimes;
    }

    public void setSearchtimes(int searchtimes) {
        this.searchtimes = searchtimes;
    }
}
