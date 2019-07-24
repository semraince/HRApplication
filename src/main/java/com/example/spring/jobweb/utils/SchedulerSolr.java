package com.example.spring.jobweb.utils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class SchedulerSolr {
   /* @Scheduled(cron = "0 * * * * *")
    public void dataImport(){
        SolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/user").build();
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("qt", "/dataimport");
        params.set("command", "full-import");
        params.set("config", "data-config.xml");
        try {
            client.query(params);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}
