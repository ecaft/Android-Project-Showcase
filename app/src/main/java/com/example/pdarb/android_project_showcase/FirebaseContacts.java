package com.example.pdarb.android_project_showcase;

public class FirebaseContacts {
    public String contactName;
    public String email;
    public String gradYear;
    public String major;
    public String teamName;
    public String teamType;
    public String title;

    public FirebaseContacts() {}

    public FirebaseContacts(String contactName, String email, String gradYear, String major, String teamName, String teamType, String title) {
        this.contactName = contactName;
        this.email = email;
        this.gradYear = gradYear;
        this.major = major;
        this.teamName = teamName;
        this.teamType = teamType;
        this.title = title;
    }

    public String getName() {
        return contactName;
    }

    public void setName(String name) {
        this.contactName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGradYear() {
        return gradYear;
    }

    public void setGradYear(String gradYear) {
        this.gradYear = gradYear;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTeam() { return teamName; }

    public void setTeam(String team) {
        this.teamName = team;
    }

    public String getType() {
        return teamType;
    }

    public void setType(String type) {
        this.teamType = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
