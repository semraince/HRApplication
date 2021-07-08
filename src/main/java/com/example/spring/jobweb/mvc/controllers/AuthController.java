package com.example.spring.jobweb.mvc.controllers;

import com.example.spring.jobweb.mvc.config.JwtTokenUtil;
import com.example.spring.jobweb.mvc.models.requests.HrLoginRequest;
import com.example.spring.jobweb.mvc.models.responses.JwtResponse;
import com.example.spring.jobweb.mvc.services.JwtUserDetailsService;
import com.nimbusds.jose.JOSEException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;




@RestController
public class AuthController {

    @Qualifier("authenticationManagerBean1")
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @RequestMapping(name = "/auth",method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody HrLoginRequest authenticationRequest)
            throws AuthenticationException, IOException, JOSEException {
        System.out.println("semra");
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        // throws authenticationException if it fails !
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        userDetailsService.setIsLinkedin(0);
        System.out.println(bcryptEncoder.encode(password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

}
