package com.example.spring.jobweb.mvc.models.dtos;

import com.example.spring.jobweb.database.models.Application;
import com.example.spring.jobweb.database.models.Job;
import com.example.spring.jobweb.database.models.User;
import com.example.spring.jobweb.utils.IModel;

public class ApplicationDto implements IModel<Application> {

    private int application_id;

    private int status;
    private  User user;
    private Job job;


    @Override
    public void getDataFromDatabaseModel(Application application) {
        this.status=application.getStatus();
        this.user=application.getUser();
        this.job=application.getJob();
        this.application_id=application.getApplication_id();

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
}
