package com.example.spring.jobweb.mvc.models.requests;

import java.util.Date;

public class JobUpdateRequest {
    private int status;
    private String activation;
    private String deactivation;
    private String title;
    private String description;
    private String per_qualities;
    private String tech_qualities;



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String  getDeactivation() {
        return deactivation;
    }

    public void setDeactivation(String deactivation) {
        this.deactivation = deactivation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
