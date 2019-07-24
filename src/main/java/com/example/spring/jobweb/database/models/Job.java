package com.example.spring.jobweb.database.models;

import com.example.spring.jobweb.mvc.services.DandelionService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "job_id")
public class Job implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="job_id")
    private int job_id;
    @Column(name="status")
    private int status;
    @Column(name="activation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activation;
    @Column(name="deactivation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivation;
    @Column(name="job_title")
    private String title;
    @Column(name="job_description")
    private String description;
    @Column(name="job_per_qualities")
    private String per_qualities;
    @Column(name="job_exp_features")
    private String tech_qualities;
    @Column(name="keywords")
    private String keywords;


    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "job")

    @JsonManagedReference
    private List<Application> applications=new ArrayList<>();
    public Job(){}

    public Job(int status, Date activation, Date deactivation, String title, String description, String per_qualities, String tech_qualities) {
        super();
        this.status = status;
        this.activation = activation;
        this.deactivation = deactivation;
        this.title = title;
        this.description = description;
        this.per_qualities = per_qualities;
        this.tech_qualities = tech_qualities;
    }

    public int getId() {
        return job_id;
    }

    public void setId(int id) {
        this.job_id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public Date getActivation() {
        return activation;
    }

    public void setActivation(Date activation) {
        this.activation = activation;
    }

    public Date getDeactivation() {
        return deactivation;
    }

    public void setDeactivation(Date deactivation) {
        this.deactivation = deactivation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPer_qualities() {
        return per_qualities;
    }

    public void setPer_qualities(String per_qualities) {
        this.per_qualities = per_qualities;
    }

    public String getTech_qualities() {
        return tech_qualities;
    }

    public void setTech_qualities(String tech_qualities) {
        this.tech_qualities = tech_qualities;
    }


    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {

    }
    public String createKeyWords(){
        String kwords=title+" "+description+" "+per_qualities+" "+tech_qualities;
        keywords=DandelionService.extractKeywords(kwords);
        return keywords;
    }

}
