package com.example.trollgg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.trollgg.dto.MatchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MatchService {

	@Value("${riot.api.key}")
	private String API_KEY;
	private String GET_MATCH_ID_RIOT_URL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/";
	private String RIOT_GET_MATCH_DATA_RIOT_URL = "https://asia.api.riotgames.com/lol/match/v5/matches/";

	public List<String> getMatchIdList(String puuid) {
		try {
			String url = GET_MATCH_ID_RIOT_URL + puuid + "/ids?start=0&count=20" + "&&api_key=" + API_KEY;

			return new RestTemplate()
				.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){})
				.getBody();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<MatchDto> getMatchData(List<String> matchIdList) {
		return matchIdList.stream()
			.map(id -> {
				try {
					String url = RIOT_GET_MATCH_DATA_RIOT_URL + id + "?api_key=" + API_KEY;
					return new RestTemplate()
						.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<MatchDto>(){})
						.getBody();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			})
			.toList();
	}
}
