package com.advats2.courseapp.model;

import java.sql.Date;
import java.util.Objects;

public class Admin extends User {
    private Date DateOfJoining;

    public Admin() {

    }
    public Admin(User user) {
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setFName(user.getFName());
        this.setLName(user.getLName());
        this.setRole(user.getRole());
    }

    public Date getDateOfJoining() {
        return DateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        DateOfJoining = dateOfJoining;
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
        return Objects.equals(this.getUsername(), ((Admin) obj).getUsername());
    }
}
