package com.example.spring.jobweb.utils;

import com.example.spring.jobweb.database.models.Job;
import com.example.spring.jobweb.database.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class SchedulerJob {
    @Autowired
    JobRepository jobRepository;
    @Scheduled(cron = "0 * * * * *")
    public void setAdvertStatuses() {
        Date date = new Date();
        for (Job job:jobRepository.findAll()) {
            System.out.println("deneme "+date);
            System.out.println("deneme "+job.getActivation());
            if (job.getActivation() != null && job.getActivation().before(date)) {
                job.setStatus(JobState.ACTIVE.getValue());
                jobRepository.save(job);

            }
            if (job.getDeactivation() != null && job.getDeactivation().before(date)) {
                job.setStatus(JobState.DEACTIVE.getValue());
                jobRepository.save(job);

            }
        }
    }
}
