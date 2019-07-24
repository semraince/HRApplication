package com.example.spring.jobweb.database.repositories;

import com.example.spring.jobweb.database.models.Education;
import org.springframework.data.repository.CrudRepository;

public interface EducationRepository extends CrudRepository<Education,Integer> {
}
