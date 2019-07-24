package com.example.spring.jobweb.mvc.controllers;

import com.example.spring.jobweb.database.models.Application;
import com.example.spring.jobweb.mvc.models.dtos.ApplicationDto;
import com.example.spring.jobweb.mvc.models.dtos.UserDto;
import com.example.spring.jobweb.mvc.models.responses.ApplicationExistResponse;
import com.example.spring.jobweb.mvc.services.ApplicationService;
import com.example.spring.jobweb.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ApplicationService applicationService;
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    private ResponseEntity<UserDto> getMe(@RequestHeader HttpHeaders headers){
        System.out.println("semra "+headers.get("authorization").get(0));
        return ResponseEntity.ok(userService.getMe(headers.get("authorization").get(0)));
    }

    @RequestMapping(value= "/apply/{jobid}/{userid}",method=RequestMethod.POST)
    private ResponseEntity<ApplicationDto> applyJob(@PathVariable("jobid") int jobId, @PathVariable("userid")int  userid) {
        return ResponseEntity.ok().body(applicationService.createApplication(jobId,userid));
    }
    @RequestMapping(value="/check/{jobid}/{userid}")
    private ResponseEntity<ApplicationExistResponse> checkApplicationExist(@PathVariable("jobid") int jobid, @PathVariable("userid")int  userid){
        return ResponseEntity.ok().body(applicationService.checkApplicationExist(jobid,userid));
    }

    /**
     * userın yaptığı tüm başvurular
     * @param userid
     * @return
     */
    @RequestMapping(value="/findApplications/{userid}",method = RequestMethod.GET)
    private ResponseEntity<List<ApplicationDto>>  findAllApplications(@PathVariable("userid") int userid){
        return ResponseEntity.ok().body(applicationService.findAllApplications(userid));
    }

    @RequestMapping(value="/abc",method = RequestMethod.GET)
    private String getBack(){
        return "semra";
    }

   /* @RequestMapping(value="/user/loginSystem",method = RequestMethod.GET){

    }*/


}
