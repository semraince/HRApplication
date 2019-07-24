package com.example.spring.jobweb.utils;

import com.example.spring.jobweb.database.models.Education;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomProducer {
    ArrayList<String> tech_qualities;
    ArrayList<String> per_qualities;
    ArrayList<Education> educations;
    ArrayList<String> universities;
    Random rand;




    ArrayList<String> department;
    public RandomProducer(){
        rand = new Random();
        educations=new ArrayList<>();
        tech_qualities=new ArrayList<String >(Arrays.asList("Node.Js","AngularJS","Angular2.0","C++","Java","Python","Unity","Unreal Engine 4","Photoshop","Maya","Blender","AI","TensorFlow","Android","IOS",
                "Swift","Java Spring","Mysql","OradleDB","KafkaDB","NoSql","JavaScript","TypeScript"));
        per_qualities=new ArrayList<String>(Arrays.asList("Creativity skills","Critical-thinking skiils","Resilience","Problem Solving skills","Analytical Skills"));
        universities=new ArrayList<String>(Arrays.asList("Boğaziçi Üniversitesi","Bahçeşehir Üniversitesi","Ege Üniversitesi","Bilkent Üniversitesi","Bahçeşehir Üniversitesi",
                "Koç Üniversitesi","Dokuz Eylül Üniversitesi","Sabancı Üniversitesi","Yeditepe Üniversitesi","İstanbul Üniversitesi","İstanbul Teknik Üniversitesi"));
        department=new ArrayList<>(Arrays.asList("Bilgisayar Mühendisliği","Elektrik Elektronik Mühendisliği","Bilişim Yönetim Sistemleri"));
        for (int i=0;i<universities.size();i++){
            for(int j=0;j<department.size();j++){
                Education education=new Education(universities.get(i),department.get(j));
                educations.add(education);
            }

        }


    }
    public String getTech_qualities() {
        int random=rand.nextInt(5);
        int tech_random=rand.nextInt(tech_qualities.size());
        return tech_qualities.get(tech_random);
    }

    public void setTech_qualities(ArrayList<String> tech_qualities) {
        this.tech_qualities = tech_qualities;
    }

    public String getPer_qualities() {
        int per_random=rand.nextInt(per_qualities.size());
        return  per_qualities.get(per_random);
    }

    public void setPer_qualities(ArrayList<String> per_qualities) {
        this.per_qualities = per_qualities;
    }

    public ArrayList<Education> getEducations() {
        int random=rand.nextInt(educations.size());
        ArrayList<Education> al2 = new ArrayList<>(educations.subList(1, 3));
        return al2;
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
    }

}
