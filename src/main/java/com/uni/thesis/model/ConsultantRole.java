package com.uni.thesis.model;


import javax.persistence.*;

@Entity
@Table(name = "consultant_role")
public class ConsultantRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int consultantroleid;
    private int consultant_id;
    private int role_id;

    public int getConsultant_id() {
        return consultant_id;
    }

    public void setConsultant_id(int consultant_id) {
        this.consultant_id = consultant_id;
    }

    public int getConsultantroleid() {
        return consultantroleid;
    }

    public void setConsultantroleid(int consultantroleid) {
        this.consultantroleid = consultantroleid;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
