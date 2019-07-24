package com.example.spring.jobweb.database.repositories;

import com.example.spring.jobweb.database.models.HumanResource;
import org.springframework.data.repository.CrudRepository;

public interface HumanResourceRepository extends CrudRepository<HumanResource,Integer> {
}
