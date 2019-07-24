package com.example.spring.jobweb.database.repositories;

import com.example.spring.jobweb.database.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    public User findByLinkedinid(String linkedinId);
    public User findByUserEmail(String email);
}
