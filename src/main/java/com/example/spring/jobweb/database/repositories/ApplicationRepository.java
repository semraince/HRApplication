package com.example.spring.jobweb.database.repositories;

import com.example.spring.jobweb.database.models.Application;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application,Integer> {
    public List<Application> findAllByStatus(int status);
}
