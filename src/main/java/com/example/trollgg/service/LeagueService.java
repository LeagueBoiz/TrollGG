package com.example.trollgg.service;

import com.example.trollgg.dto.LeagueDto;
import com.example.trollgg.dto.LeagueEntryDto;
import com.example.trollgg.error.ExternalApiCallException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class LeagueService {
    @Value("${riot.api.key}")
    private String API_KEY;
    private static final String LEAGUE_DATA_RIOT_URL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/";

    public LeagueDto getLeagueData(String summonerId) {
        try {
            String url = LEAGUE_DATA_RIOT_URL + summonerId + "?api_key=" + API_KEY;

            Set<LeagueEntryDto> response = new RestTemplate()
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Set<LeagueEntryDto>>(){})
                    .getBody();

            return new LeagueDto(response);

        } catch (Exception e) {
            throw new ExternalApiCallException(e.getMessage());
        }
    }

    public LeagueEntryDto getFistLeagueData(String summonerId) {
        return getLeagueData(summonerId).leagueData().stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(()->new NoSuchElementException("소환사의 정보를 찾을 수 없습니다."));
    }
}
