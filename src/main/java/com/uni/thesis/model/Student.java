package com.uni.thesis.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentid;
    private String username;
    private String name;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topicid", referencedColumnName = "topicid")
    private Topic topicid;
    private String faculty;
    private String specialisation;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "student_role",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "studentid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleid")})
    private List<Role> studentRoles;

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
    public List<Role> getRoles() {
        return studentRoles;
    }

    public void setRoles(List<Role> roles) {
        this.studentRoles = roles;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Topic getTopicid() {
        return topicid;
    }

    public void setTopicid(Topic topicid) {
        this.topicid = topicid;
    }
}
