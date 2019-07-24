package com.example.spring.jobweb.mvc.models.dtos;

import com.example.spring.jobweb.database.models.Job;
import com.example.spring.jobweb.utils.IModel;

import java.util.Date;

public class JobDto implements IModel<Job> {
    private int id;
    private int status;
    private Date activation;
    private Date deactivation;
    private String title;
    private String description;
    private String per_qualities;
    private String tech_qualities;

    @Override
    public void getDataFromDatabaseModel(Job job) {
        id=job.getId();
        status=job.getStatus();
        activation=job.getActivation();
        deactivation=job.getDeactivation();
        title=job.getTitle();
        description=job.getDescription();
        per_qualities=job.getPer_qualities();
        tech_qualities=job.getTech_qualities();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getActivation() {
        return activation;
    }

    public void setActivation(Date activation) {
        this.activation = activation;
    }

    public Date getDeactivation() {
        return deactivation;
    }

    public void setDeactivation(Date deactivation) {
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
