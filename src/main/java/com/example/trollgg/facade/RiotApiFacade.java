package com.example.trollgg.facade;

import java.util.List;

import com.example.trollgg.dto.LeagueDto;
import com.example.trollgg.dto.MatchDto;
import com.example.trollgg.service.LeagueService;
import org.springframework.stereotype.Component;

import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.service.MatchService;
import com.example.trollgg.service.SummonerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RiotApiFacade {

	private final SummonerService summonerService;
	private final LeagueService leagueService;

	private final MatchService matchService;

	public SummonerDto getSummonerData(String summonerName) {
		return summonerService.getSummonerData(summonerName);
	}

	public LeagueDto getLeague(String summonerName){
		return leagueService.getLeagueData(summonerService.getSummonerData(summonerName).id());
	}

	public List<MatchDto> getMatch(String summonerName) {
		List<String> matchIdList = matchService.getMatchIdList(summonerService.getSummonerData(summonerName).puuid());
		return matchService.getMatchData(matchIdList);
	}
}
