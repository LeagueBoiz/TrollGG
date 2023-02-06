package com.example.trollgg.facade;

import com.example.trollgg.dto.LeagueDto;
import com.example.trollgg.dto.LeagueEntryDto;
import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.dto.SummonerProfileDto;
import com.example.trollgg.dto.match.MatchDto;
import com.example.trollgg.enums.Rank;
import com.example.trollgg.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class RiotApiFacade {

	private final SummonerService summonerService;
	private final LeagueService leagueService;
	private final MatchService matchService;
	private final DataDragonService dataDragonService;
	private final FormulaService formulaService;


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

//	public SummonerProfileDto getSummonerProfile(String summonerName) {
//		SummonerDto summonerDto = summonerService.getSummonerData(summonerName);
//		LeagueEntryDto leagueEntryDto =leagueService.getFistLeagueData(summonerDto.id());
//		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
//		String winningRate = NumberUtils.winningRate(leagueEntryDto.wins(),leagueEntryDto.wins()+leagueEntryDto.losses());
//
//		return new SummonerProfileDto(leagueEntryDto,profileUrl,winningRate);
//	}

	public SummonerProfileDto getSummonerRankProfile(String summonerName) {
		SummonerDto summonerDto = summonerService.getSummonerData(summonerName);
		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
		LeagueDto leagueDto = leagueService.getLeagueData(summonerDto.id());
		return getSummonerRankProfile(profileUrl, leagueDto);
	}

	private SummonerProfileDto getSummonerRankProfile(String profileUrl, LeagueDto leagueDto) {
		return leagueDto.leagueData().stream()
				.filter(Objects::nonNull)
				.findFirst()
				.map(leagueEntryDto -> {
					String winningRate = formulaService.getRankWinningRate(leagueEntryDto);
					return new SummonerProfileDto(leagueEntryDto, profileUrl, winningRate);
				})
				.orElseGet(() -> new SummonerProfileDto(Rank.UNRANKED));
	}

}
