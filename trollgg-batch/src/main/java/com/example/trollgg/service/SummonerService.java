package com.example.trollgg.service;

import com.example.trollgg.entity.Summoner;
import com.example.trollgg.repository.SummonerRepository;
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
	private static final String SUMMONER_DATA_RIOT_URL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";

	private final DataDragonService dataDragonService;

	private final SummonerRepository summonerRepository;

	public SummonerDto getSummonerData(String summonerName) {
		String url = SUMMONER_DATA_RIOT_URL + summonerName + "?api_key=" + API_KEY;
		return new RestTemplate().getForObject(url, SummonerDto.class);
	}

	public void firstEnroll(String summonerName){
		SummonerDto summonerDto = getSummonerData(summonerName);
		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
		Summoner summoner = Summoner.builder()
				.summonerName(summonerName)
				.profileUrl(profileUrl)
				.puuid(summonerDto.puuid())
				.summonerLevel(summonerDto.summonerLevel())
				.encryptedId(summonerDto.id())
				.accountId(summonerDto.accountId())
				.build();
		summonerRepository.save(summoner);
	}
}
