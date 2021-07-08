package com.example.spring.jobweb.mvc.controllers;

import com.example.spring.jobweb.database.models.Education;
import com.example.spring.jobweb.database.models.User;
import com.example.spring.jobweb.mvc.models.dtos.Deneme;
import com.example.spring.jobweb.mvc.models.dtos.JobDto;
import com.example.spring.jobweb.mvc.models.requests.JobInsertRequest;
import com.example.spring.jobweb.mvc.models.requests.JobUpdateRequest;
import com.example.spring.jobweb.mvc.models.requests.UserLoginRequest;
import com.example.spring.jobweb.mvc.services.DandelionService;
import com.example.spring.jobweb.mvc.services.JobService;
import com.example.spring.jobweb.mvc.services.UserService;
import com.example.spring.jobweb.utils.JobState;
import com.example.spring.jobweb.utils.StringRequest;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin//(origins = "*", maxAge = 3600, allowedHeaders = { "x-auth-token", "x-requested-with" })
public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/getAllJobs", method = RequestMethod.GET)
    private ResponseEntity<List<JobDto>> getAllJobs(){
        String jsDate="2013-3-22 10:13:00";
        Date javaDate= null;
        Date date=new Date();
        System.out.println(date);
        try {
            javaDate = new SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(jsDate);
            System.out.println(javaDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(jobService.getAllJobByStatus(JobState.ACTIVE.getValue()));
    }
    /*@RequestMapping(value = "/search",method = RequestMethod.GET)
    private SolrDocumentList search(@RequestBody StringRequest text){
        System.out.println(text.getText());
        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/user").build();
        SolrQuery query = new SolrQuery();
        query.setQuery("allField: ("+text.getText()+")");
        query.setHighlightSimplePost("</strong>");
        query.setHighlightSimplePre("<strong>");
        query.setStart(0);
        try {
            QueryResponse response = client.query(query);
            SolrDocumentList results = response.getResults();
            System.out.println(response.getResults().size());
            for (int i = 0; i < results.size(); ++i) {
                System.out.println(results.get(i));
            }
            return results;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }*/
    @RequestMapping(value = "/humlg",method = RequestMethod.GET)
    public ResponseEntity<?>  login(){
        //String newUrl="http://localhost:9090/login";
       /*String newUrl="http://192.168.1.29:4200/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(newUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);*/
       return ResponseEntity.ok(HttpStatus.OK);

        //return ResponseEntity.ok().body(newUrl);
    }
    @RequestMapping(value="/getJob/{jobId}",method = RequestMethod.GET)
    private ResponseEntity<JobDto> getJob(@PathVariable("jobId") int jobId ){
        return ResponseEntity.ok().body(jobService.getJob(jobId));
    }
    @RequestMapping(value = "/getPhoto",method = RequestMethod.GET)
    public HttpEntity<byte[]> getPhoto() {
        byte[] image = new byte[0];
        try {
            image = org.apache.commons.io.FileUtils.readFileToByteArray(new File("classpath:src/main/resources/static/images/user.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);
        return new HttpEntity<byte[]>(image, headers);
    }
 /*if (text.contains(":")) {
        query.setHighlightRequireFieldMatch(true);
    }
        query.setHighlight(true);
        query.addHighlightField("*");
        query.setHighlightSimplePost("</strong>");
        query.setHighlightSimplePre("<strong>");
    QueryResponse response = new QueryResponse(solrClient);
        try {
        response = solrClient.query(query);
    } catch (SolrServerException | IOException ex) {
        Logger.getLogger(HRController.class.getName()).log(Level.SEVERE, null, ex);
    }
    Map<Heading, Map<String, List<String>>> hitMap = new TreeMap<>();
        for (Entry<String, Map<String, List<String>>> entry : response.getHighlighting().entrySet()) {//String key, String alan adı diğer string de içindekiler olmalı
        Applicant applicant = applicantService.findApplicant(entry.getKey());
        hitMap.put(new Heading(applicant.getFirstname() + ' ' + applicant.getLastname(), entry.getKey()), entry.getValue());
    }
        return hitMap;
    /*@RequestMapping(value="/addJob",method=RequestMethod.POST)
    private ResponseEntity<JobDto> addJob(@RequestBody JobInsertRequest jobInsertRequest){
        return ResponseEntity.ok().body(jobService.addJob(jobInsertRequest));
    }*/

   /*
    @RequestMapping(value ="/updateJob/{jobId}",method =RequestMethod.PUT)
    private ResponseEntity<JobDto> updateJob(@PathVariable("jobId") int jobId, @RequestBody JobUpdateRequest jobUpdateRequest){
        return ResponseEntity.ok().body(jobService.updateJob(jobId,jobUpdateRequest));
    }
    @RequestMapping(value="/deleteJob/{jobId}",method = RequestMethod.DELETE)
    private ResponseEntity deleteJob(@PathVariable("jobId")int jobId){
        jobService.deleteJob(jobId);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/denemee",method =RequestMethod.GET)
    private void x(){
        Education education=new Education("Yeditepe","CSE");
        Education education1=new Education("Bogazici","CSE");
        UserLoginRequest userLoginRequest =new UserLoginRequest("djdjd","semra","ince","x");
        userService.addUser(userLoginRequest);

    }*/

}
