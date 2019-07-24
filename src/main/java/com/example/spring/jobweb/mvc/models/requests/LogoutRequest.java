package com.example.spring.jobweb.mvc.models.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class LogoutRequest {

    private boolean isDeleteToken;
    @JsonCreator
    public LogoutRequest(@JsonProperty(value="isDeleteToken",defaultValue="false") boolean isDeleteToken) {
        this.isDeleteToken = isDeleteToken;
    }

    public boolean isDeleteToken() {
        return isDeleteToken;
    }
}
