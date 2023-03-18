package com.example.trollgg.service;

import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.entity.Summoner;
import com.example.trollgg.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class SummonerService {

	@Value("${riot.api.key}")
	private String API_KEY;
	private static final String SUMMONER_DATA_URL_PREFIX = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/";
	private static final String SUMMONER_DATA_BY_SUMMONER_NAME = SUMMONER_DATA_URL_PREFIX + "by-name/";
	private static final String SUMMONER_DATA_BY_SUMMONER_ID = SUMMONER_DATA_URL_PREFIX;
	private final DataDragonService dataDragonService;

	private final SummonerRepository summonerRepository;

	public SummonerDto getSummonerDataByName(String summonerName) {
		String url = SUMMONER_DATA_BY_SUMMONER_NAME + summonerName + "?api_key=" + API_KEY;
		return new RestTemplate().getForObject(url, SummonerDto.class);
	}

	public SummonerDto getSummonerDataById(String encryptedId) {
		String url = SUMMONER_DATA_BY_SUMMONER_ID + encryptedId + "?api_key=" + API_KEY;
		return new RestTemplate().getForObject(url, SummonerDto.class);
	}

	@Transactional
	public Summoner firstEnroll(String summonerName) {
		SummonerDto summonerDto = getSummonerDataByName(summonerName);
		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
		Summoner summoner = Summoner.builder()
				.summonerName(summonerName)
				.profileUrl(profileUrl)
				.puuid(summonerDto.puuid())
				.summonerLevel(summonerDto.summonerLevel())
				.encryptedId(summonerDto.id())
				.accountId(summonerDto.accountId())
				.build();
		return summonerRepository.save(summoner);
	}
}
