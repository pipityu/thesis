package com.uni.thesis.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "consultant")
public class Consultant{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int consultantid;
    private String username;
    private String name;
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "consultant_role",
            joinColumns = {@JoinColumn(name = "consultant_id", referencedColumnName = "consultantid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleid")})
    private List<Role> consultantRoles;

    @OneToMany(mappedBy = "consultantid")
    private List<Topic> topics;


    public int getConsultantid() {
        return consultantid;
    }

    public void setConsultantid(int consultantid) {
        this.consultantid = consultantid;
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

    public List<Role> getConsultantRoles() {
        return consultantRoles;
    }

    public void setConsultantRoles(List<Role> consultantRoles) {
        this.consultantRoles = consultantRoles;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
