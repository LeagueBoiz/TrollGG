package com.example.trollgg.service;

import java.util.List;
import java.util.Objects;

import com.example.trollgg.entity.Match;
import com.example.trollgg.error.ExternalApiCallException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.trollgg.dto.match.MatchDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MatchService {

	@Value("${riot.api.key}")
	private String API_KEY;
	private static final String MATCH_ID_RIOT_URL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/";
	private static final String MATCH_DATA_RIOT_URL = "https://asia.api.riotgames.com/lol/match/v5/matches/";

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

	public List<MatchDto> getMatchData(List<String> matchIdList) {
		return matchIdList.stream()
			.map(id -> {
				try {
					String url = MATCH_DATA_RIOT_URL + id + "?api_key=" + API_KEY;
					return new RestTemplate()
						.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<MatchDto>(){})
						.getBody();
				} catch (Exception e) {
					throw new ExternalApiCallException(e.getMessage());
				}
			})
			.filter(Objects::nonNull)
			.toList();
	}

	public void saveMatch(String matchId) {
		String url = MATCH_DATA_RIOT_URL + matchId + "?api_key=" + API_KEY;
		MatchDto matchDto = new RestTemplate()
				.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<MatchDto>() {
				})
				.getBody();


	}
}
