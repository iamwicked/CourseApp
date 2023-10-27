package com.advats2.courseapp.model;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String role;

    private String FName;
    private String LName;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                '}';
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
        return Objects.equals(this.getUsername(), ((User) obj).getUsername());
    }
}