package com.ecaft.pdarb.android_project_showcase;

import java.util.ArrayList;
import java.util.List;

public class FirebaseProject {
    public String descrip;
    public String teamName;
    public String teamType;
    public String intro;
    public String majors;
    public String resumeBook;
    public FirebaseProject() {}

    public FirebaseProject(String descrip, String intro, String majors, String resumeBook, String teamName, String teamType) {
        this.descrip = descrip;
        this.teamName = teamName;
        this.teamType = teamType;
        this.intro = intro;
        this.majors = majors;
        this.resumeBook = resumeBook;
    }

    public String getDescription() {
        return descrip;
    }

    public void setDescription(String description) {
        this.descrip = description;
    }

    public String getName() {
        return teamName;
    }

    public void setName(String name) {
        this.teamName = name;
    }

    public String getType() {
        return teamType;
    }

    public void setType(String type) {
        this.teamType = type;
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

    public String getResumeBook() {
        return resumeBook;
    }

    public void setResumeBook(String resumeBook) {
        this.resumeBook = resumeBook;
    }

    public List<String> getMajorList(){
        ArrayList<String> m = new ArrayList<String>();
        String[] arr = majors.split(",");
        for(String s: arr){
            m.add(s.trim());
        }
        return m;
    }



}
