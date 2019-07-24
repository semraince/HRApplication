package com.example.spring.jobweb.utils;

public enum JobState {
    DEACTIVE(0),
    ACTIVE(1);

    private final int job_value;

    JobState(final int job_value){
        this.job_value=job_value;
    }

    public int getValue(){
        return job_value;
    }
}
