package com.advats2.courseapp.model;

import java.util.List;
import java.util.Objects;

public class Educator extends User {
    private String Gender;
    private String Degree;
    private int year;
    private String University;
    private String About;
    private String AdminUserName;
    private List<String> emails;
    private List<String> fields;

    public Educator() {

    }

    public Educator(User user) {
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setFName(user.getFName());
        this.setLName(user.getLName());
        this.setRole(user.getRole());
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getAdminUserName() {
        return AdminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        AdminUserName = adminUserName;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(getClass() != obj.getClass()) {
            return false;
        }
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        return Objects.equals(this.getUsername(), ((Educator) obj).getUsername());
    }
}
