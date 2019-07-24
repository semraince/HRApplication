package com.example.spring.jobweb.mvc.models.dtos;

import com.example.spring.jobweb.database.models.Education;
import com.example.spring.jobweb.database.models.User;
import com.example.spring.jobweb.utils.IModel;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

public class UserDto implements IModel<User> {
    private int userId;
    private String linkedin_id;
    private String userName;


    private String userSurname;
    private String per_qualities;
    private String tech_qualities;
    private String email;
    private String location;
    private boolean isInBlackList;
    private String reasonBlackList;
    private List<Education> educations;


    private String profilePicture;

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    @Override
    public void getDataFromDatabaseModel(User user) {
        userId=user.getUserId();
        linkedin_id=user.getLinkedin_id();
        userName=user.getUserName();
        userSurname=user.getUserSurname();
        per_qualities=user.getPer_qualities();
        tech_qualities=user.getTech_qualities();
        email=user.getEmail();
        profilePicture=user.getProfilePicture();
        educations=user.getEducations();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isInBlackList() {
        return isInBlackList;
    }

    public void setInBlackList(boolean inBlackList) {
        isInBlackList = inBlackList;
    }

    public String getReasonBlackList() {
        return reasonBlackList;
    }

    public void setReasonBlackList(String reasonBlackList) {
        this.reasonBlackList = reasonBlackList;
    }
    public String getLinkedin_id() {
        return linkedin_id;
    }

    public void setLinkedin_id(String linkedin_id) {
        this.linkedin_id = linkedin_id;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
