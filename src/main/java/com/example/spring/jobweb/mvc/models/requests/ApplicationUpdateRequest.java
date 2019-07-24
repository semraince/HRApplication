package com.example.spring.jobweb.mvc.models.requests;

import com.example.spring.jobweb.database.models.Job;
import com.example.spring.jobweb.database.models.User;

public class ApplicationUpdateRequest {
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

}
