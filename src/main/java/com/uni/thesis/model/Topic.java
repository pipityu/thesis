package com.uni.thesis.model;

import javax.persistence.*;

@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int topicid;
    @ManyToOne
    @JoinColumn(name = "consultantid")
    private Consultant consultantid;
    private String name;
    private String description;

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public Consultant getConsultantid() {
        return consultantid;
    }

    public void setConsultantid(Consultant id) {
        this.consultantid = id;
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
}
