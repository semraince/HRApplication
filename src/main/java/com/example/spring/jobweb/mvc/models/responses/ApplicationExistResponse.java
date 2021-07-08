package com.example.spring.jobweb.mvc.models.responses;

public class ApplicationExistResponse {
    private int isExist;

    public int getIsExist() {
        return isExist;
    }

    public void setIsExist(int isExist) {
        this.isExist = isExist;
    }

    public ApplicationExistResponse(int isExist) {
        this.isExist = isExist;
    }
}
