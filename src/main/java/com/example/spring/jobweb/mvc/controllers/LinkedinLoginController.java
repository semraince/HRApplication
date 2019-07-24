package com.example.spring.jobweb.mvc.controllers;

import com.example.spring.jobweb.mvc.config.JwtTokenUtil;
import com.example.spring.jobweb.mvc.models.dtos.UserDto;
import com.example.spring.jobweb.mvc.models.requests.UserLoginRequest;
import com.example.spring.jobweb.mvc.models.responses.JwtResponse;
import com.example.spring.jobweb.mvc.services.JwtUserDetailsService;
import com.example.spring.jobweb.mvc.services.UserService;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import javax.json.JsonArray;
import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletResponse;






@Controller
public class LinkedinLoginController {
    private String clientId = "776ejcmhmwbheo";
    private String clientSecret = "7DUbvVsCAIzNFbyW";
    String nameOfUser,surnameOfUser,idOfUser,email;
    @Autowired
    UserService userService;

    /***
     * Adding for jwt
     */

    @Qualifier("authenticationManagerBean2")
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    public String redirectUrl="http://192.168.1.29:9090/user/login";//"http://localhost:9090/user/login";


    @RequestMapping(value = "/user")
    public String invoke() {
        return "SEMRS";
    }

    @RequestMapping(value = "/user/logiin", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> invokeRedirect() {
        String authorizationUri="https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUrl+"&scope=r_liteprofile%20r_emailaddress";
        //return "redirect:" + authorizationUri;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(authorizationUri));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

   /* @RequestMapping(value = "/user/logiin", method = RequestMethod.GET)
    public String invokeRedirect() {
        String authorizationUri="https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUrl+"&scope=r_liteprofile%20r_emailaddress";
        return "redirect:" + authorizationUri;
    }*/

    @GetMapping("/user/login")
    @ResponseBody
    //now store your authorization code
    public  ResponseEntity<?>home (@RequestParam("code") String authorizationCode) throws JSONException {
        String linedkinDetailUri = "\n" +
                "https://api.linkedin.com/v2/me?projection=(id,firstName,lastName,profilePicture(displayImage~:playableStreams))";
        String linkedinEmailUri="https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))";
        try {
            //to trade your authorization code for access token
            String accessTokenUri = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code=" + authorizationCode + "&redirect_uri=" + redirectUrl + "&client_id=" + clientId + "&client_secret=" + clientSecret + "";

            // linkedin api to get linkedidn profile detail


            //store your access token
            RestTemplate restTemplate = new RestTemplate();

            String accessTokenRequest = restTemplate.getForObject(accessTokenUri, String.class);
            JSONObject jsonObjOfAccessToken = new JSONObject(accessTokenRequest);
            String accessToken = jsonObjOfAccessToken.get("access_token").toString();
            JsonObject profileData = LinkedinLoginController.sendGetRequest(linedkinDetailUri, accessToken);
            JsonObject emailAddress=LinkedinLoginController.sendGetRequest(linkedinEmailUri, accessToken);
            UserDto userDto=createUser(profileData,emailAddress);
            authenticate(profileData.getString("id"), userDto.getEmail());
            System.out.println("buradayım");
            userDetailsService.setIsLinkedin(1);
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(userDto.getEmail());

            final String token = jwtTokenUtil.generateToken(userDetails);
            System.out.println("token is "+token);
            //return ResponseEntity.ok(new JwtResponse(token));
            //return "http://localhost:4200/login?"+token;
           String newUrl="http://localhost:4200/login?token="+token;
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(newUrl));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);


        } catch (JSONException e) {
            System.out.println("Json error");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        //return new ResponseEntity<>("Successfully login", HttpStatus.OK);
    }


    private static JsonObject sendGetRequest(String urlString, String accessToken) throws Exception {

            URL url = new URL(urlString);
            TimeUnit.SECONDS.sleep(10);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);
            con.setRequestProperty("cache-control", "no-cache");
            con.setRequestProperty("X-Restli-Protocol-Version", "2.0.0");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();

            JsonReader jsonReader = Json.createReader(new StringReader(jsonString.toString()));
            JsonObject jsonObject = jsonReader.readObject();

            return jsonObject;


    }
    //if not exist create if exist update
    private UserDto createUser(JsonObject... jsonObject){
        JsonObject profileData=jsonObject[0];
        JsonObject emailAddress=jsonObject[1];
        System.out.println(emailAddress.toString());
        System.out.println(profileData.toString());
        JsonObject ic=profileData.getJsonObject("firstName");
        email=emailAddress.getJsonArray("elements").getJsonObject(0).getJsonObject("handle~").getString("emailAddress");
        System.out.println(email);
        JsonObject name=profileData.getJsonObject("firstName").getJsonObject("localized");
        Set<String> set=name.keySet();
        Iterator<String> iterator =set.iterator();
        while(iterator.hasNext()){
            String currentDynamicKey = (String)iterator.next();
            nameOfUser=name.getString(currentDynamicKey);
            System.out.println(nameOfUser);
        }
        JsonObject surname=profileData.getJsonObject("lastName").getJsonObject("localized");
        set=surname.keySet();
        iterator =set.iterator();
        while(iterator.hasNext()){
            String currentDynamicKey = (String)iterator.next();
            surnameOfUser=surname.getString(currentDynamicKey);
            System.out.println(surnameOfUser);
        }
        String profile_picture=null;
        if(profileData.containsKey("profilePicture")) {
            JsonArray identifiers = profileData.getJsonObject("profilePicture").getJsonObject("displayImage~").getJsonArray("elements").
                    getJsonObject(0).getJsonArray("identifiers");
            System.out.println(identifiers);
            profile_picture = identifiers.getJsonObject(0).getString("identifier");
            System.out.println(profile_picture);
        }
        else{
            profile_picture="aggs";
        }
        idOfUser=profileData.getString("id");
        System.out.println(idOfUser);
        UserLoginRequest loginRequest=new UserLoginRequest(idOfUser,nameOfUser,surnameOfUser,email,profile_picture);
        return userService.checkUser(loginRequest);



    }
    private void authenticate(String linkedinId, String email) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, linkedinId));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
        @RequestMapping("/user/login3")
        public String home() {
            return "linkedin";
        }

}
