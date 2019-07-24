package com.example.spring.jobweb.mvc.services;

import com.example.spring.jobweb.database.models.Application;
import com.example.spring.jobweb.database.models.Job;
import com.example.spring.jobweb.database.models.User;
import com.example.spring.jobweb.database.repositories.ApplicationRepository;
import com.example.spring.jobweb.database.repositories.JobRepository;
import com.example.spring.jobweb.database.repositories.UserRepository;
import com.example.spring.jobweb.mvc.models.dtos.ApplicationDto;
import com.example.spring.jobweb.mvc.models.dtos.JobDto;
import com.example.spring.jobweb.mvc.models.dtos.UserDto;
import com.example.spring.jobweb.mvc.models.requests.ApplicationRequest;
import com.example.spring.jobweb.mvc.models.requests.ApplicationUpdateRequest;
import com.example.spring.jobweb.mvc.models.responses.ApplicationExistResponse;
import com.example.spring.jobweb.utils.ApplicationState;
import com.example.spring.jobweb.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelConverter modelConverter;

    public List<ApplicationDto> getAllApplications(){
        List<Application> applications=new ArrayList<>();
        applicationRepository.findAll().forEach(applications::add);
        System.out.println(applications.size()+" ");
        List<ApplicationDto> applicationDtos=modelConverter.getModelListFromDataList(applications,ApplicationDto.class);
        return applicationDtos;
    }
    public ApplicationDto updateApplication(ApplicationUpdateRequest applicationUpdateRequest){
        int status=applicationUpdateRequest.getStatus();
        if(status==ApplicationState.ACCEPTING.getValue()){

        }
        else if(status==ApplicationState.REJECTING.getValue()){

        }
        return new ApplicationDto();

    }
    public ApplicationExistResponse checkApplicationExist(int jobid, int userid){
        ApplicationExistResponse applicationExistResponse=new ApplicationExistResponse(1);
        List<JobDto> jobDtos=findAllJobs(userid);
        for (int i=0;i<jobDtos.size();i++){
            if(jobDtos.get(i).getId()==jobid){
                return applicationExistResponse;
            }
        }
        applicationExistResponse.setIsExist(0);
      return applicationExistResponse;
    }

    public ApplicationDto createApplication(int jobid,int userid){//(ApplicationRequest applicationRequest){
        //Application application=new Application(applicationRequest.getUser(),applicationRequest.getJob(), ApplicationState.WAITING.getValue());
       // if (checkApplicationExist(jobid,userid)==true){return null;}
        User user=userRepository.findById(userid).get();
        Job job=jobRepository.findById(jobid).get();
        Application application=new Application(user,job,ApplicationState.WAITING.getValue());
        applicationRepository.save(application);
        EmailService.send(user.getUserName(),user.getUserSurname(),user.getEmail(),application.getJob().getTitle(),ApplicationState.WAITING.getValue());
        ApplicationDto applicationDto=modelConverter.getModelFromData(application,ApplicationDto.class);
        return applicationDto;
    }
    public ApplicationDto getApplication(int id){

        Application application=applicationRepository.findById(id).get();
        ApplicationDto applicationDto=modelConverter.getModelFromData(application,ApplicationDto.class);
        return applicationDto;
    }

    public void deleteApplication(int id){
        applicationRepository.deleteById(id);
    }

    public List<JobDto> findAllJobs(int userId){
        //ApplicationDto applicationDto=getApplication(applicationId);
        User user=userRepository.findById(userId).get();
        List<Job> appliedJobs=new ArrayList<>();
        for(Application job:user.getApplications()){
            appliedJobs.add(job.getJob());
        }
        List<JobDto> JobDtos=modelConverter.getModelListFromDataList(appliedJobs,JobDto.class);
        return JobDtos;
    }

    public ApplicationDto saveApplication(Application application) {
        applicationRepository.save(application);
        ApplicationDto applicationDto=modelConverter.getModelFromData(application,ApplicationDto.class);
        return applicationDto;
    }
    public ApplicationDto changeStatus(int applicationId,int status){
        Application application=applicationRepository.findById(applicationId).get();
        application.setStatus(status);
        applicationRepository.save(application);
        User user=application.getUser();
        EmailService.send(user.getUserName(),user.getUserSurname(),user.getEmail(),application.getJob().getTitle(),status);
        ApplicationDto applicationDto=modelConverter.getModelFromData(application,ApplicationDto.class);
        return applicationDto;
    }

    /**
     * userın başvuruları
     * @param userId
     * @return
     */
    public List<ApplicationDto> findAllApplications(int userId) {
        User user = userRepository.findById(userId).get();
        List<Application> applications = user.getApplications();
        System.out.println(applications.size());
        List<ApplicationDto> applicationDtos = modelConverter.getModelListFromDataList(applications, ApplicationDto.class);
        return applicationDtos;
    }

    /** yeni*/
    /**
     * hbir işe başvuran kişileri listeler
     * @param jobId
     * @return
     */
    public List<UserDto> findAllUsers(int jobId){
        List<User> userList=new ArrayList<>();
        Job job=jobRepository.findById(jobId).get();
        for(Application application:job.getApplications()){
            userList.add(application.getUser());
        }
        List<UserDto> UserDtos=modelConverter.getModelListFromDataList(userList,UserDto.class);
        return UserDtos;
    }
    public List<UserDto> getAllAppbyStatus(int jobid,int status){
        List<User> userList=new ArrayList<>();
        Job job=jobRepository.findById(jobid).get();
        for(Application application:job.getApplications()){
            if(application.getStatus()==status){
                userList.add(application.getUser());
            }
        }
        List<UserDto> UserDtos=modelConverter.getModelListFromDataList(userList,UserDto.class);
        return UserDtos;
    }

    /*public List<UserDto> findAllRelatedUsers(int jobId){
        List<User> userList=new ArrayList<>();
        Job job=jobRepository.findById(jobId).get();
        for(Application application:job.getApplications()){
            User user=application.getUser();
            user.createKeywords();
            userRepository.save(user);
            application.calculateSimilarity();
            applicationRepository.save(application);
        }

    }*/
    /*@RequestMapping("/applications/{advertId}")
    public String findAllApplications(@PathVariable int advertId, Model model) {
        List<Application> applications = advertService.findAdvert(advertId).getApplications();
        Collections.sort(applications, new Comparator<Application>() {
            @Override
            public int compare(Application a1, Application a2) {
                return -Double.compare(a1.getSimilarity(), a2.getSimilarity());
            }
        });*/





}
