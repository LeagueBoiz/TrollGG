package com.example.trollgg.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.trollgg.dto.SummonerDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SummonerService {

	@Value("${riot.api.key}")
	private String API_KEY;
	private String RIOT_URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";

	public SummonerDto getSummonerData(String summonerName) {
		String url = RIOT_URL + summonerName + "?api_key=" + API_KEY;
		return new RestTemplate().getForObject(url, SummonerDto.class);
	}
}
