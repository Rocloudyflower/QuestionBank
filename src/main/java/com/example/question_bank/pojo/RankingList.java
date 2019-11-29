package com.example.question_bank.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "rankinglist")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class RankingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
    @JoinColumn(name = "useremail")
    private User user;

    @ManyToOne
    @JoinColumn(name = "unitid")
    private Unit unit;

    private int score;
    private int times;

    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}

    public User getUser() {return this.user;}
    public void setUser(User user) {this.user = user;}

    public Unit getUnit() {return this.unit;}
    public void setUnit(Unit unit) {this.unit = unit;}

    public int getScore() {return this.score;}
    public void setScore(int score) {this.score = score;}

    public int getTimes() {return this.times;}
    public void setTimes(int times) {this.times = times;}

}
