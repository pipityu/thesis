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
    private String status;
    @OneToOne(mappedBy = "topicid")
    private Student student;

    public Topic(){}

    public Topic(int topicid, Consultant consultant, String name, String description, String status){
        this.topicid = topicid;
        this.consultantid = consultant;
        this.name = name;
        this.description = description;
        this.status = status;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
