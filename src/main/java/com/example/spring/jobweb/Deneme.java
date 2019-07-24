package com.example.spring.jobweb;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Deneme {
    @RequestMapping(value="/hello")
    public String sayHi(){
        return "Hello World";
    }
}
