package com.example.pdarb.android_project_showcase;

import java.util.ArrayList;

public class FirebaseProject {
    public String description;
    public String name;
    public String type;
    public String intro;
    public String majors;
    public ArrayList<FirebaseContacts> members;
    public FirebaseProject() {}

    public FirebaseProject(String id, String description, String name, String type, String intro, String majors) {
        this.description = description;
        this.name = name;
        this.type = type;
        this.intro = intro;
        this.majors = majors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String type) {
        this.intro = intro;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }
}
