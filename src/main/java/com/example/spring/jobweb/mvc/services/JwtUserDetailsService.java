package com.example.spring.jobweb.mvc.services;

import java.util.ArrayList;

import com.example.spring.jobweb.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;
    private int isLinkedin;
    private String hrpassword="$2a$10$jDZxaLWoxIu8CEu.JukO9.H.kvV.tH05btJbiyghKzC63uIiZfSRm";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(isLinkedin==1) {
            com.example.spring.jobweb.database.models.User user = userRepository.findByUserEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            System.out.println("linkedin " + user.getLinkedin_id());
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getLinkedin_id(),
                    new ArrayList<>());
        }
        else
        {
            System.out.println("girdim");
            return new org.springframework.security.core.userdetails.User(username, hrpassword,
                    new ArrayList<>());
        }
    }

    public int getIsLinkedin() {
        return isLinkedin;
    }

    public void setIsLinkedin(int isLinkedin) {
        this.isLinkedin = isLinkedin;
    }


}
