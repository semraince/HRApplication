package com.example.spring.jobweb.utils;

import com.example.spring.jobweb.database.models.Application;
//WHEN CALLING ApplicationState.WAITING.getValue();
public enum ApplicationState {
    WAITING(0),
    ACCEPTING(1),
    REJECTING(2);

    private final int value;

    ApplicationState(final int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
