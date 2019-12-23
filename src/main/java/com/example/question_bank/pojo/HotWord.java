package com.example.question_bank.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hotwords")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Proxy(lazy = false)
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
