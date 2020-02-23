package com.uni.thesis.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleid;
    private String name;

    @ManyToMany(mappedBy = "studentRoles")
    private List<Student> students;

    @ManyToMany(mappedBy = "consultantRoles")
    private List<Consultant> consultants;

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
