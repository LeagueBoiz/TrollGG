package com.example.trollgg.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class SummonerController {
    final static String API_KEY = "RGAPI-a9761a00-248f-4204-9d8f-91c09f879608";

    @RequestMapping(value="/search", method=RequestMethod.GET)
    public String searchSummoner(Model model, HttpServletRequest httpServletRequest) {
        BufferedReader br = null;
        String SummonerName = httpServletRequest.getParameter("title");
        try{
            String urlstr = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+
                    SummonerName		+"?api_key="+API_KEY;
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8")); // 여기에 문자열을 받아와라.
            StringBuilder result = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) { // 그 받아온 문자열을 계속 br에서 줄단위로 받고 출력하겠다.
                result.append(line);
            }
            System.out.println(result);
            JSONParser parser= new JSONParser(String.valueOf(result));
            JSONObject object= (JSONObject) parser.parse();
            String name = object.get("name").toString();
            return name;
//            JSONParser jsonParser = new JSONParser(result);
//            JSONObject k = (JSONObject) jsonParser.parse();
//            int profileIconId = (int) k.get("profileIconId");
//            String name = k.get("name").getAsString();
//            String puuid = k.get("puuid").getAsString();
//            long summonerLevel = k.get("summonerLevel").getAsLong();
//            long revisionDate = k.get("revisionDate").getAsLong();
//            String id = k.get("id").getAsString();
//            String accountId = k.get("accountId").getAsString();
//            temp = new Summoner(profileIconId,name,puuid,summonerLevel,revisionDate,id,accountId);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "a";
        }
    }
}
