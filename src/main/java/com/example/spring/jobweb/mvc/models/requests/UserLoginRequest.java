package com.example.spring.jobweb.mvc.models.requests;

import com.example.spring.jobweb.database.models.Education;

import java.util.List;
public class UserLoginRequest {
    private String userName;
    private String linkedin_id;
    private String userSurname;
    private String per_qualities;
    private String tech_qualities;
    private String email;
    private String profile_picture;
    private boolean isInBlackList;


    List<Education> educations;



    public UserLoginRequest(String linkedin_id,String userName, String userSurname, String email,String profile_picture){
        this.linkedin_id=linkedin_id;
        this.userName = userName;
        this.userSurname = userSurname;
        this.email = email;
        this.profile_picture=profile_picture;

    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
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

    public String getLinkedin_id() {
        return linkedin_id;
    }

    public void setLinkedin_id(String linkedin_id) {
        this.linkedin_id = linkedin_id;
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

    public boolean isInBlackList() {
        return isInBlackList;
    }

    public void setInBlackList(boolean inBlackList) {
        isInBlackList = inBlackList;
    }
    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
