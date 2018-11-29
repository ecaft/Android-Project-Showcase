package com.example.pdarb.android_project_showcase;

public class FirebaseContacts {
    public String name;
    public String email;
    public String gradyear;
    public String major;
    public String team;
    public String type;

    public FirebaseContacts() {}

    public FirebaseContacts(String name, String email, String gradyear, String major, String team, String type) {
        this.name = name;
        this.email = email;
        this.gradyear = gradyear;
        this.major = major;
        this.team = team;
        this.type = type;
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

    public String getGradyear() {
        return gradyear;
    }

    public void setGradyear(String gradyear) {
        this.gradyear = gradyear;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
