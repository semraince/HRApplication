package com.example.spring.jobweb.database.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HumanResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hr_id;
    private String hr_name;
    private String hr_surname;

    public HumanResource() { }

    public HumanResource(String hr_name, String hr_surname) {
        this.hr_name = hr_name;
        this.hr_surname = hr_surname;
    }

    public int getHr_id() {
        return hr_id;
    }

    public void setHr_id(int hr_id) {
        this.hr_id = hr_id;
    }

    public String getHr_name() {
        return hr_name;
    }

    public void setHr_name(String hr_name) {
        this.hr_name = hr_name;
    }

    public String getHr_surname() {
        return hr_surname;
    }

    public void setHr_surname(String hr_surname) {
        this.hr_surname = hr_surname;
    }
}
