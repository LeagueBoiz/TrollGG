package com.example.trollgg.service;

import com.example.assign.dto.riotDto.SummonerDto;
import com.example.assign.dto.riotDto.match.MatchDto;
import com.example.assign.error.ExternalApiCallException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RiotApiService {
    @Value("${riot.api.key}")
    private String API_KEY;

    // summoner-v4 /lol/summoner/v4/summoners/by-name/{summonerName}
    private static final String SUMMONER_DATA_RIOT_URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    // match-v5 /lol/match/v5/matches/by-puuid/{puuid}/ids
    private static final String MATCH_ID_RIOT_URL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/";
    // match-v5 /lol/match/v5/matches/{matchId}
    private static final String MATCH_DATA_RIOT_URL = "https://asia.api.riotgames.com/lol/match/v5/matches/";


    // summoner-v4 /lol/summoner/v4/summoners/by-name/{summonerName}
    //소환사이름으로 puuid, encryptid 등 찾기
    public SummonerDto getSummonerData(String summonerName) {
        try {
            String url = SUMMONER_DATA_RIOT_URL + summonerName + "?api_key=" + API_KEY;
            return new RestTemplate().getForObject(url, SummonerDto.class);
        }catch (Exception e){
            throw new ExternalApiCallException(e.getMessage());
        }
    }
    // match-v5 /lol/match/v5/matches/by-puuid/{puuid}/ids
    //puuid 를 통해 Matchlist 를 찾기
    public List<String> getMatchIdList(String puuid) {
        try {
            String url = MATCH_ID_RIOT_URL + puuid + "/ids?start=0&count=20" + "&&api_key=" + API_KEY;

            return new RestTemplate()
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                    })
                    .getBody();

        } catch (Exception e) {
            throw new ExternalApiCallException(e.getMessage());
        }
    }
    // match-v5 /lol/match/v5/matches/{matchId}
    // matchId 로 match에 대한 데이터를 가져오기
    public MatchDto getMatchData(String matchId){
        try {
            String url = MATCH_DATA_RIOT_URL + matchId + "?api_key=" + API_KEY;
            return new RestTemplate()
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<MatchDto>(){})
                    .getBody();
        } catch (Exception e) {
            throw new ExternalApiCallException(e.getMessage());
        }
    }
}
