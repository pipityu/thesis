package com.uni.thesis.model;


import javax.persistence.*;

@Entity
@Table(name = "student_role")
public class StudentRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentroleid;
    private int student_id;
    private int role_id;

    public int getStudentroleid() {
        return studentroleid;
    }

    public void setStudentroleid(int studentroleid) {
        this.studentroleid = studentroleid;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
