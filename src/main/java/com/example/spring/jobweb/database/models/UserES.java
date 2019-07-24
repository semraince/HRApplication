package com.example.spring.jobweb.database.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserES {
    @Field
    private int user_id;

    @Field
    private String linkedinid;
    @Field
    private String user_name;
    @Field
    private String user_surname;
    @Field
    private String per_qualities;
    @Field
    private String tech_qualities;
    @Field
    private String userEmail;
    @Field
    private boolean isInBlackList;
    @Field
    private String reasonForBlackList;
    @Field
    private List<Integer> education_id=new ArrayList<>();
    @Field
    private List<String> university=new ArrayList<>();
    @Field
    private List<String>  department=new ArrayList<>();

    public UserES(int user_id, String linkedinid, String user_name, String user_surname, String per_qualities, String tech_qualities, String userEmail, boolean isInBlackList, String reasonForBlackList, List<Integer> education_id, List<String> university, List<String> department) {
        this.user_id = user_id;
        this.linkedinid = linkedinid;
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.per_qualities = per_qualities;
        this.tech_qualities = tech_qualities;
        this.userEmail = userEmail;
        this.isInBlackList = isInBlackList;
        this.reasonForBlackList = reasonForBlackList;
        this.education_id = education_id;
        this.university = university;
        this.department = department;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLinkedinid() {
        return linkedinid;
    }

    public void setLinkedinid(String linkedinid) {
        this.linkedinid = linkedinid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public String getPer_qualities() {
        return per_qualities;
    }

    public void setPer_qualities(String per_qualities) {
        this.per_qualities = per_qualities;
    }

    public String getTech_qualities() {
        return tech_qualities;
    }

    public void setTech_qualities(String tech_qualities) {
        this.tech_qualities = tech_qualities;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isInBlackList() {
        return isInBlackList;
    }

    public void setInBlackList(boolean inBlackList) {
        isInBlackList = inBlackList;
    }

    public String getReasonForBlackList() {
        return reasonForBlackList;
    }

    public void setReasonForBlackList(String reasonForBlackList) {
        this.reasonForBlackList = reasonForBlackList;
    }

    public List<Integer> getEducation_id() {
        return education_id;
    }

    public void setEducation_id(List<Integer> education_id) {
        this.education_id = education_id;
    }

    public List<String> getUniversity() {
        return university;
    }

    public void setUniversity(List<String> university) {
        this.university = university;
    }

    public List<String> getDepartment() {
        return department;
    }

    public void setDepartment(List<String> department) {
        this.department = department;
    }
}
