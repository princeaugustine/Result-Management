package com.udom.school.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Students {

    private String fullname;
    private String parentphone;
    @Id
    private String candidate;
    private String password;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getParentphone() {
        return parentphone;
    }

    public void setParentphone(String parentphone) {
        this.parentphone = parentphone;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Students{" +
                "fullname='" + fullname + '\'' +
                ", parentphone='" + parentphone + '\'' +
                ", candidate='" + candidate + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
