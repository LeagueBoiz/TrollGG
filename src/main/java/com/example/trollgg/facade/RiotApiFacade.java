package com.example.trollgg.facade;

import com.example.trollgg.dto.LeagueDto;
import com.example.trollgg.dto.LeagueEntryDto;
import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.dto.SummonerProfileDto;
import com.example.trollgg.dto.match.MatchDto;
import com.example.trollgg.service.DataDragonService;
import com.example.trollgg.service.LeagueService;
import com.example.trollgg.service.MatchService;
import com.example.trollgg.service.SummonerService;
import com.example.trollgg.util.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RiotApiFacade {

	private final SummonerService summonerService;
	private final LeagueService leagueService;
	private final MatchService matchService;
	private final DataDragonService dataDragonService;

	public SummonerDto getSummonerData(String summonerName) {
		return summonerService.getSummonerData(summonerName);
	}

	public LeagueDto getLeague(String summonerName){
		String id = summonerService.getSummonerData(summonerName).id();
		return leagueService.getLeagueData(id);
	}

	public List<MatchDto> getMatch(String summonerName) {
		List<String> matchIdList = matchService.getMatchIdList(summonerService.getSummonerData(summonerName).puuid());
		return matchService.getMatchData(matchIdList);
	}

	public SummonerProfileDto getSummonerProfile(String summonerName) {
		SummonerDto summonerDto = summonerService.getSummonerData(summonerName);
		LeagueEntryDto leagueEntryDto =leagueService.getFistLeagueData(summonerDto.id());
		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
		String winningRate = NumberUtils.winningRate(leagueEntryDto.wins(),leagueEntryDto.wins()+leagueEntryDto.losses());

		return new SummonerProfileDto(leagueEntryDto,profileUrl,winningRate);
	}

	public Object getSummonerRankProfile(String summonerName) {
		SummonerDto summonerDto = summonerService.getSummonerData(summonerName);
		LeagueEntryDto leagueEntryDto =leagueService.getFistLeagueData(summonerDto.id());
		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
		String winningRate = NumberUtils.winningRate(leagueEntryDto.wins(),leagueEntryDto.wins()+leagueEntryDto.losses());

		return new SummonerProfileDto(leagueEntryDto,profileUrl,winningRate);
	}
}
