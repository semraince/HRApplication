package com.example.spring.jobweb.database.models;

import com.example.spring.jobweb.mvc.services.DandelionService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.Search;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.search.annotations.*;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
//property = "user_id")
@Indexed
public class User implements Serializable {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;


    @Column(name="linkedinid")
    private String linkedinid;

    @Column(name="user_name")
    private String user_name;

    @Column(name="user_surname")
    private String user_surname;
    @Column(name="per_qualities")

    private String per_qualities;

    @Column(name="tech_qualities")
    private String tech_qualities;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="is_in_blacklist")
    private boolean isInBlackList;

    @Column(name="reason_for_blacklist")
    private String reasonForBlackList;

    @Column(name="profile_picture")
    private String profilePicture;

    @Column(name="keywords")
    private String keywords;


    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    @JsonManagedReference
    private List<Education> educations=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    @JsonManagedReference
    private List<Application> applications=new ArrayList<>();



    public User() {
    }


    public User(String linkedinid, String user_name, String user_surname, String per_qualities, String tech_qualities, String userEmail, boolean isInBlackList, String profilePicture, List<Education> educations) {
        this.linkedinid = linkedinid;
        this.user_name = user_name;
        this.user_surname = user_surname;
        this.per_qualities = per_qualities;
        this.tech_qualities = tech_qualities;
        this.userEmail = userEmail;
        this.isInBlackList = isInBlackList;
        this.profilePicture = profilePicture;
        this.educations = educations;

    }
    /* public User(String userName, String userSurname, String per_qualities, String tech_qualities, String email, boolean isInBlackList) {
        this.user_name = userName;
        this.user_surname = userSurname;
        this.per_qualities = per_qualities;
        this.tech_qualities = tech_qualities;
        this.userEmail = email;
        this.isInBlackList = isInBlackList;
    }*/

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String userName) {
        this.user_name = userName;
    }

    public String getUserSurname() {
        return user_surname;
    }

    public void setUserSurname(String userSurname) {
        this.user_surname = userSurname;
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

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public boolean isInBlackList() {
        return isInBlackList;
    }

    public void setInBlackList(boolean inBlackList) {
        isInBlackList = inBlackList;
    }

    public String getReasonBlackList() {
        return reasonForBlackList;
    }

    public void setReasonBlackList(String reasonBlackList) {
        this.reasonForBlackList = reasonBlackList;
    }
    public String getLinkedin_id() {
        return linkedinid;
    }

    public void setLinkedin_id(String linkedin_id) {
        this.linkedinid = linkedin_id;
    }


    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String createKeywords(){
        String kwords=per_qualities+tech_qualities;
        keywords= DandelionService.extractKeywords(kwords);
        return keywords;
    }


}
