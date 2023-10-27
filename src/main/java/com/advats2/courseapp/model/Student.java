package com.advats2.courseapp.model;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Student extends User {
    private int Age;
    private String City;
    private String State;
    private int Pincode;
    private String Specialisation;
    private String Degree;
    private String College;
    private Date DOB;
    private List<String> emails;
    private List<Long> PhoneNos;

    public Student() {
        System.out.println("student");
    }

    public Student(User user) {
        this.setUsername(user.getUsername());
        System.out.println("hello");
        this.setPassword(user.getPassword());
        this.setFName(user.getFName());
        this.setLName(user.getLName());
        this.setRole(user.getRole());
        System.out.println("yo");
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public int getPincode() {
        return Pincode;
    }

    public void setPincode(int pincode) {
        Pincode = pincode;
    }

    public String getSpecialisation() {
        return Specialisation;
    }

    public void setSpecialisation(String specialisation) {
        Specialisation = specialisation;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
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
        return Objects.equals(this.getUsername(), ((Student) obj).getUsername());
    }
}
