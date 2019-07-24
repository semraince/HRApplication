package com.example.spring.jobweb.mvc.services;



import com.google.gson.JsonElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.json.JsonObject;

/** Used Entity Extraction API*/
public class DandelionService {
    public static String extractKeywords(String exttext)  {
        String keywords=null;
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost request=new HttpPost("https://api.dandelion.eu/datatxt/nex/v1");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("text",exttext));
        params.add(new BasicNameValuePair("lang","en"));
        params.add(new BasicNameValuePair("min_confidence","0.6"));
        params.add(new BasicNameValuePair("token","96323c99e8094e0e9163d41ed1e7be6c"));
        try {
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response=httpClient.execute(request);
            String reStr= EntityUtils.toString(response.getEntity(),"UTF-8");
            JSONParser jsonParser=new JSONParser();
            JSONObject jsonObject=(JSONObject) jsonParser.parse(reStr);
            JSONArray annotations = (JSONArray) jsonObject.get("annotations");
            Iterator it = annotations.iterator();
            StringBuilder str = new StringBuilder();
            while (it.hasNext()){
                JSONObject slide= (JSONObject) it.next();
                String title = (String)slide.get("spot");
                str.append(title+" ");
            }
            keywords=str.toString();
            System.out.println(keywords);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return keywords;
    }
/**Used Text Similarity API*/
    public static double getSimilarity(String jobText,String applicantText){
        double similarity_value=0;
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost request=new HttpPost("https://api.dandelion.eu/datatxt/sim/v1");
        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("text1",jobText));
        params.add(new BasicNameValuePair("text2",applicantText));
        params.add(new BasicNameValuePair("lang","en"));
        params.add(new BasicNameValuePair("bow","never"));
        params.add(new BasicNameValuePair("token","96323c99e8094e0e9163d41ed1e7be6c"));
        try {
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response=httpClient.execute(request);
            String reStr= EntityUtils.toString(response.getEntity(),"UTF-8");
            JSONParser jsonParser=new JSONParser();
            JSONObject jsonObject=(JSONObject) jsonParser.parse(reStr);
            System.out.println(jsonObject);
            //JSONObject similarity = (JSONObject) jsonObject.get("similarity");
            similarity_value= (double) jsonObject.get("similarity");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return similarity_value;

    }

    /*public static double calcSimilarity(String advert, String applicant) {
        HttpPost httpPost = new HttpPost("https://api.dandelion.eu/datatxt/sim/v1");
        List<NameValuePair> params = new ArrayList<>(5);
        params.add(new BasicNameValuePair("text1", advert));
        params.add(new BasicNameValuePair("text2", applicant));
        params.add(new BasicNameValuePair("lang", "en"));
        params.add(new BasicNameValuePair("bow", "never"));
        params.add(new BasicNameValuePair("token", "a397094f43f840a1ba7f20b875baf5ae"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Dandelion.class.getName()).log(Level.SEVERE, null, ex);
        }
        Double similarity = new Double(0);
        try {
            similarity = (Double) ((JSONObject) new JSONParser().parse(EntityUtils.toString(HttpClients.createDefault().execute(httpPost).getEntity(), "UTF-8"))).get("similarity");
        } catch (ParseException | IOException ex) {
            Logger.getLogger(Dandelion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return similarity;
    }*/
}
