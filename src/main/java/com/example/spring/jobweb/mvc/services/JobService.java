package com.example.spring.jobweb.mvc.services;

import com.example.spring.jobweb.database.models.Job;
import com.example.spring.jobweb.database.repositories.JobRepository;
import com.example.spring.jobweb.mvc.models.dtos.JobDto;
import com.example.spring.jobweb.mvc.models.requests.JobInsertRequest;
import com.example.spring.jobweb.mvc.models.requests.JobUpdateRequest;
import com.example.spring.jobweb.utils.ModelConverter;
import org.apache.http.HttpStatus;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelConverter modelConverter;

    public List<JobDto> getAllJobs(){
        List<Job> jobs=new ArrayList<>();
        jobRepository.findAll().forEach(jobs::add);
        List<JobDto> jobDtos=modelConverter.getModelListFromDataList(jobs,JobDto.class);
        return jobDtos;
    }
    public List<JobDto> getAllJobByStatus(int status){
        List<Job> jobs=new ArrayList<>();
        jobRepository.findAllByStatus(status).forEach(jobs::add);
        List<JobDto> jobDtos=modelConverter.getModelListFromDataList(jobs,JobDto.class);
        return jobDtos;
    }
    public JobDto addJob(JobInsertRequest jobInsertRequest){
        Date activationDate = null;
        Date deactivationDate=null;
        try {
            activationDate = new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(jobInsertRequest.getActivation());
            deactivationDate = new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(jobInsertRequest.getDeactivation());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Job job=new Job(jobInsertRequest.getStatus(),activationDate,deactivationDate,jobInsertRequest.getTitle(),
                jobInsertRequest.getDescription(),jobInsertRequest.getPer_qualities(),jobInsertRequest.getTech_qualities());
        jobRepository.save(job);
        System.out.println(getJob(job.getId()).getDescription());
        JobDto jobDto=modelConverter.getModelFromData(job,JobDto.class);
        return jobDto;
    }
    public JobDto getJob(int id){

        Job job=jobRepository.findById(id).get();
        JobDto jobDto=modelConverter.getModelFromData(job,JobDto.class);
        return jobDto;
    }
    public JobDto updateJob(int jobId, JobUpdateRequest jobUpdateRequest){
        Job job=jobRepository.findById(jobId).get();
        Date activationDate=null;
        Date deactivationDate=null;
        if(jobUpdateRequest.getActivation()!=null) {
            try {
                activationDate = new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(jobUpdateRequest.getActivation());
                System.out.println(activationDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(jobUpdateRequest.getDeactivation()!=null){
            try {
                deactivationDate   = new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(jobUpdateRequest.getDeactivation());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (job==null){
            throw new ResourceNotFoundException("Job Not Found");
        }
        if(activationDate!=job.getActivation()){
           // job.setActivation(jobUpdateRequest.getActivation());
            job.setActivation(activationDate);
        }
        if(deactivationDate!=job.getDeactivation()){
            //job.setDeactivation(jobUpdateRequest.getDeactivation());
            job.setDeactivation(deactivationDate);
        }
        if(jobUpdateRequest.getDescription()!=null){
            job.setDescription(jobUpdateRequest.getDescription());
        }
        if(jobUpdateRequest.getPer_qualities()!=null){
            job.setPer_qualities(jobUpdateRequest.getPer_qualities());
        }
        if(jobUpdateRequest.getStatus()!=job.getStatus()){
            job.setStatus(jobUpdateRequest.getStatus());
        }
        if(jobUpdateRequest.getTitle()!=null){
            job.setTitle(jobUpdateRequest.getTitle());
        }
        if(jobUpdateRequest.getTech_qualities()!=null){
            job.setTech_qualities(jobUpdateRequest.getTech_qualities());
        }
        jobRepository.save(job);
        return modelConverter.getModelFromData(job,JobDto.class);
    }
    public void deleteJob(int id){
        jobRepository.deleteById(id);
    }

}
