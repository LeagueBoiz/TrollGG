package com.example.trollgg.service;

import com.example.trollgg.dto.LeagueDto;
import com.example.trollgg.dto.LeagueEntryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class LeagueService {
    @Value("${riot.api.key}")
    private String API_KEY;
    private String RIOT_URL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/";

    public LeagueDto getLeagueData(String summonerId) {
        try {
            String url = RIOT_URL + summonerId + "?api_key=" + API_KEY;

            Set<LeagueEntryDTO> response = new RestTemplate()
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Set<LeagueEntryDTO>>(){})
                    .getBody();

            return new LeagueDto(response);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
