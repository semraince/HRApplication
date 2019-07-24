package com.example.spring.jobweb.mvc.services;

import com.example.spring.jobweb.database.models.Application;
import com.example.spring.jobweb.database.models.Job;
import com.example.spring.jobweb.database.models.User;
import com.example.spring.jobweb.database.repositories.EducationRepository;
import com.example.spring.jobweb.database.repositories.JobRepository;
import com.example.spring.jobweb.database.repositories.UserRepository;
import com.example.spring.jobweb.mvc.config.JwtTokenUtil;
import com.example.spring.jobweb.mvc.models.dtos.UserDto;
import com.example.spring.jobweb.mvc.models.requests.UserLoginRequest;
import com.example.spring.jobweb.utils.ApplicationState;
import com.example.spring.jobweb.utils.ModelConverter;
import com.example.spring.jobweb.utils.RandomProducer;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.lucene.search.Query;
import org.hibernate.search.engine.ProjectionConstants;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    ApplicationService applicationService;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private ModelConverter modelConverter;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    EntityManager entityManager;
    public  List<UserDto> getAllUsers(){
        List<User> users=new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        List<UserDto> userDtos=modelConverter.getModelListFromDataList(users,UserDto.class);
        return userDtos;
    }
    public UserDto getMe(String token){
        System.out.println(token);
        String jwtToken= token.substring(7);
        User user=userRepository.findByUserEmail(jwtTokenUtil.getUsernameFromToken(jwtToken));
        System.out.println(user.getEmail());
        UserDto userDto=modelConverter.getModelFromData(user,UserDto.class);
        return userDto;
    }
    public UserDto checkUser(UserLoginRequest userloginRequest){
        //User user =userRepository.findByLinkedinid(userloginRequest.getLinkedin_id());
        User user =userRepository.findByUserEmail(userloginRequest.getEmail());
        if(user==null){
            return addUser(userloginRequest);

        }
        user.setEmail(userloginRequest.getEmail());
        user.setUserName(userloginRequest.getUserName());
        user.setUserSurname(userloginRequest.getUserSurname());
        System.out.println(user.getEducations().get(0).getDepartment());
        userRepository.save(user);
        UserDto userDto=modelConverter.getModelFromData(user,UserDto.class);
        return userDto;

    }
    public UserDto getUser(int userid){
        User user=userRepository.findById(userid).get();
        UserDto userDto=modelConverter.getModelFromData(user,UserDto.class);
        return userDto;

    }
    public UserDto addUser(UserLoginRequest userLoginRequest){
        RandomProducer randomProducer=new RandomProducer();
        User user=new User(bcryptEncoder.encode(userLoginRequest.getLinkedin_id()),userLoginRequest.getUserName(),userLoginRequest.getUserSurname(),randomProducer.getPer_qualities(),
                randomProducer.getTech_qualities(),userLoginRequest.getEmail(),false,userLoginRequest.getProfile_picture(),randomProducer.getEducations());

        /*System.out.println(userLoginRequest.getEducations().get(0).getDepartment());*/

        for (int i=0;i<randomProducer.getEducations().size();i++){
            randomProducer.getEducations().get(i).setUser(user);
        }
        userRepository.save(user);
        UserDto userDto=modelConverter.getModelFromData(user,UserDto.class);


        return userDto;

    }
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public String applyJob(int jobId,int userid){
        User user=userRepository.findById(userid).get();
        if(user==null){
            return "Not Found";
        }
        boolean checkUser=user.isInBlackList();
        if (checkUser){
            return "Not valid";
        }
        Job job=jobRepository.findById(jobId).get();
        Application application=new Application();
        application.setUser(user);
        application.setJob(job);
        application.setStatus(0);
        return "semra";

    }
    public void addBlackList(int userid,String reason){
        User user=userRepository.findById(userid).get();
        user.setInBlackList(true);
        user.setReasonBlackList(reason);
        userRepository.save(user);
        for(Application application:user.getApplications()){
            application.setStatus(ApplicationState.REJECTING.getValue());
            applicationService.saveApplication(application);
            EmailService.send(user.getUserName(),user.getUserSurname(),user.getEmail(),application.getJob().getTitle(),ApplicationState.REJECTING.getValue());
        }
    }
    public void deleteFromBlackList(int userid){
        User user=userRepository.findById(userid).get();
        user.setInBlackList(true);
        user.setReasonBlackList(null);
        userRepository.save(user);
    }
    private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
