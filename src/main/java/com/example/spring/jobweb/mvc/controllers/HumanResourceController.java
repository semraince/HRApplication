package com.example.spring.jobweb.mvc.controllers;

import com.example.spring.jobweb.mvc.models.dtos.ApplicationDto;
import com.example.spring.jobweb.mvc.models.dtos.JobDto;
import com.example.spring.jobweb.mvc.models.dtos.UserDto;
import com.example.spring.jobweb.mvc.models.requests.JobInsertRequest;
import com.example.spring.jobweb.mvc.models.requests.JobUpdateRequest;
import com.example.spring.jobweb.mvc.models.requests.UserLoginRequest;
import com.example.spring.jobweb.mvc.services.ApplicationService;
import com.example.spring.jobweb.mvc.services.JobService;
import com.example.spring.jobweb.mvc.services.UserService;
import com.example.spring.jobweb.utils.StringRequest;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/hr")
@CrossOrigin
public class HumanResourceController {
    @Autowired
    JobService jobService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationService applicationService;

    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    private ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    /**Yeni bir iş yaratmak
     *
     * @param jobInsertRequest
     *
     */


    @RequestMapping(value="/addJob",method=RequestMethod.POST)
    private ResponseEntity<JobDto> addJob(@RequestBody JobInsertRequest jobInsertRequest){
        return ResponseEntity.ok().body(jobService.addJob(jobInsertRequest));
    }

    /**
     * Bir işi bulmak
     * @param jobId
     * @return
     */


    @RequestMapping(value="/getJob/{jobId}",method = RequestMethod.GET)
    private ResponseEntity<JobDto> getJob(@PathVariable("jobId") int jobId ){
        return ResponseEntity.ok().body(jobService.getJob(jobId));
    }
    /**
     * Bütün işlerin tamamını bulmak
     */
    @RequestMapping(value = "/getAllJobs",method = RequestMethod.GET)
    private ResponseEntity <List<JobDto>> getAllJobs(){
        return ResponseEntity.ok().body(jobService.getAllJobs());
    }
    /**
     * Bir işi güncellemek
     */
    @RequestMapping(value ="/updateJob/{jobId}",method =RequestMethod.PUT)
    private ResponseEntity<JobDto> updateJob(@PathVariable("jobId") int jobId, @RequestBody JobUpdateRequest jobUpdateRequest){
        System.out.println(jobUpdateRequest.getActivation());
        return ResponseEntity.ok().body(jobService.updateJob(jobId,jobUpdateRequest));
    }

    /**
     * Bir işi silmek
     * @param jobId
     * @return
     */
    @RequestMapping(value="/deleteJob/{jobId}",method = RequestMethod.DELETE)
    private ResponseEntity deleteJob(@PathVariable("jobId")int jobId){
        jobService.deleteJob(jobId);
        return ResponseEntity.ok().build();
    }


    /**
     * Bir başvuruyu silmek
     * @param applicationid
     * @return
     */
    @RequestMapping(value = "/deleteApplication/{applicationId}",method = RequestMethod.DELETE)
    private ResponseEntity deleteApplication(@PathVariable("applicationid")int applicationid){
        applicationService.deleteApplication(applicationid);
        return ResponseEntity.ok().build();
    }

    /**
     * Kullanıcıyı kara listeye eklemek
     * @param userid
     * @param text
     * @return
     */
    /*TODO: change String to class *
     */
    @RequestMapping(value="/addBlackList/{userid}",method = RequestMethod.PUT)
    private ResponseEntity addBlackList(@PathVariable ("userid") int userid,@RequestBody String text){
        userService.addBlackList(userid,text);
        return  ResponseEntity.ok().build();
    }

    /**
     * Kullanıcıyı kara listeden kaldırmak
     * @param userid
     * @return
     */
    @RequestMapping(value="/deleteBlackList/{userid}",method = RequestMethod.PUT)
    private ResponseEntity deleteBlackList(@PathVariable ("userid") int userid){
        userService.deleteFromBlackList(userid);
        return ResponseEntity.ok().build();
    }

    /**
     * Bütün işlere yapılan başvuruları görüntülemek
     * @return
     */
    @RequestMapping(value="/applications",method = RequestMethod.GET)
    private ResponseEntity<List<ApplicationDto>> findAllApplications(){
        return ResponseEntity.ok().body(applicationService.getAllApplications());
    }

    /**
     * Belirli bir kullanıcıyı çağırmak
     * @param userid
     * @return
     */

    @RequestMapping(value = "/getUser/{userid}",method = RequestMethod.GET)
    private ResponseEntity<UserDto> getUser(@PathVariable("userid")int userid){
        return ResponseEntity.ok().body(userService.getUser(userid));
    }

    /**
     * Bir başvuruyu accept,reject etmek
     * @param applicationId
     * @param status
     * @return
     */
   @RequestMapping(value = "/changeApplicationStatus/{applicationId}/{status}",method  =RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ApplicationDto> changeApplicationStatus(@PathVariable ("applicationId") int applicationId,@PathVariable ("status") int status){
       System.out.println("status is"+status);
        return ResponseEntity.ok().body(applicationService.changeStatus(applicationId,status));
    }

    /**
     * Kullanıcının başvurduğu işlerin tamamı
     * @param userid
     * @return
     */
    /**yeni*/
    @RequestMapping(value = "/appOfUser/{userid}",method = RequestMethod.GET)
    private ResponseEntity<List<ApplicationDto>> findAllAppOfUser(@PathVariable("userid") int userid){
        return ResponseEntity.ok().body(applicationService.findAllApplications(userid));
    }

    /**
     * bir joba başvuran kullanıcıları görmek
     *
     */
    /**yeni*/
    @RequestMapping(value = "job/{jobid}",method = RequestMethod.GET)
    private ResponseEntity<List<UserDto>> findAllAppforJob(@PathVariable ("jobid") int jobId){
        return ResponseEntity.ok().body(applicationService.findAllUsers(jobId));
    }

    /**
     * joblar active ve deactive durumuna göre filtrelenir.
     * @return
     */
    @RequestMapping(value = "/getAllJobs/{status}", method = RequestMethod.GET)
    private ResponseEntity<List<JobDto>> getAllJobsbyStatus(@PathVariable("status") int status){
        return ResponseEntity.ok().body(jobService.getAllJobByStatus(status));
    }


    @RequestMapping(value = "/getAppbyStatus/{jobId}/{status}",method = RequestMethod.GET)
    private ResponseEntity<List<UserDto>> getAllAppbyStatus(@PathVariable("jobId")int jobid,@PathVariable("status")int status){
        return ResponseEntity.ok().body(applicationService.getAllAppbyStatus(jobid,status));
    }

    /**
     * en uygun başvuraları başta sıralar.
     * @param jobId
     * @return
     */
   /* @RequestMapping(value="job/related/{jobid}",method = RequestMethod.GET)
    private ResponseEntity<List<UserDto>> findMostRelatedApp(@PathVariable("jobid")int jobId){
        return ResponseEntity.ok().body(applicationService.findAllRelatedUsers(jobId));
    }*/

    /***
     * arama yapmak için
     * @param text
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
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

    }

    //@RequestMapping
   /*@RequestMapping(value = "/denemee",method =RequestMethod.GET)
    private void x(){
        Education education=new Education("Yeditepe","CSE");
        Education education1=new Education("Bogazici","CSE");
        UserLoginRequest userLoginRequest =new UserLoginRequest("djdjd","semra","ince","x");
        userService.addUser(userLoginRequest);
    }*/
}
