package com.example.spring.jobweb.mvc.models.requests;

import com.example.spring.jobweb.database.models.Job;
import com.example.spring.jobweb.database.models.User;

public class ApplicationRequest {
    private int application_id;

    private int status;
    private User user;
    private Job job;

    public ApplicationRequest(int application_id, int status, User user, Job job) {
        this.application_id = application_id;
        this.status = status;
        this.user = user;
        this.job = job;
    }

    public int getApplication_id() {
        return application_id;
    }

    public void setApplication_id(int application_id) {
        this.application_id = application_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
