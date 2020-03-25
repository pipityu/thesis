package com.uni.thesis.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "step")
public class Step{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int stepid;
    private int topicid;
    private String name;
    private String description;
    private LocalDate deadline;
    private int percentage;
    private int stepstatus;


    public Step(){}

    public int getStepid() {
        return stepid;
    }

    public void setStepid(int stepid) {
        this.stepid = stepid;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStepstatus() {
        return stepstatus;
    }

    public void setStepstatus(int stepstatus) {
        this.stepstatus = stepstatus;
    }
}
