package com.example.spring.jobweb.database.models;

import com.example.spring.jobweb.mvc.services.DandelionService;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "application_id")
public class Application implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="application_id")
    private int application_id;


    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    @JsonBackReference
    private User user;


    @ManyToOne
    @JoinColumn(name="job_id",nullable = false)
    @JsonBackReference
    private Job job;

    private int status;

    private double similarity;


    public Application() {
    }

    public Application(User user, Job job, int status) {
        super();
        this.user = user;
        this.job = job;
        this.status = status;
    }

    public int getApplication_id() {
        return application_id;
    }

    public void setApplication_id(int application_id) {
        this.application_id = application_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
    public double calculateSimilarity(){
        double similarity=DandelionService.getSimilarity(job.getKeywords(),user.getKeywords());
        return similarity;
    }
}
