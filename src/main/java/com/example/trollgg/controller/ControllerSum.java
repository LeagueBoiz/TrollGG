package com.example.trollgg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ControllerSum {
    private ObjectMapper objectMapper = new ObjectMapper();
    final static String API_KEY = "";
   @PostMapping("/sum/{sumName}")
    public void SumId(@PathVariable String sumName){
        SearchBySumDto searchBySumDto;
       String serverUrl = "https://kr.api.riotgames.com";

       try {
           HttpClient client = HttpClientBuilder.create().build();

           HttpGet request = new HttpGet(serverUrl + "/lol/summoner/v4/summoners/by-name/" + sumName + "?api_key=" + API_KEY);

           HttpResponse response = client.execute(request);

           if(response.getStatusLine().getStatusCode() != 200){
               System.out.println("api에서 값을 구할수없습니다");
           }
           HttpEntity entity = response.getEntity();
           searchBySumDto = objectMapper.readValue(entity.getContent(), SearchBySumDto.class);
           System.out.println(searchBySumDto.getName());
           System.out.println(searchBySumDto.getSummonerLevel());
       } catch (IOException e){
           e.printStackTrace();
       }
   }
}
