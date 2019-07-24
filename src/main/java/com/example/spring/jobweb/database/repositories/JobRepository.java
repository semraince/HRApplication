package com.example.spring.jobweb.database.repositories;

import com.example.spring.jobweb.database.models.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job,Integer> {
    public List<Job> findAllByStatus(int status);
}
