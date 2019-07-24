package com.example.spring.jobweb.database.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Education implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="education_id")
    private int education_id;
    @Column(name="university")
    private String university;

    @Column(name="department")
    private String department;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")*/
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Education() {
    }

    public Education(String university, String department) {
        super();
        this.university = university;
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    public int getEducation_id() {
        return education_id;
    }

    public void setEducation_id(int education_id) {
        this.education_id = education_id;
    }
}
