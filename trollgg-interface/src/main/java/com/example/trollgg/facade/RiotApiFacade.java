package com.example.trollgg.facade;

import com.example.trollgg.dto.LeagueDto;
import com.example.trollgg.dto.LeagueEntryDto;
import com.example.trollgg.dto.ScoreDto.TrollScoreDto;
import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.dto.SummonerProfileDto;
import com.example.trollgg.dto.match.MatchDto;
import com.example.trollgg.entity.Summoner;
import com.example.trollgg.error.ResourceNotFoundException;
import com.example.trollgg.repository.SummonerRepository;
import com.example.trollgg.service.DataDragonService;
import com.example.trollgg.service.LeagueService;
import com.example.trollgg.service.MatchService;
import com.example.trollgg.service.SummonerService;
import com.example.trollgg.util.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RiotApiFacade {

	private final SummonerService summonerService;
	private final LeagueService leagueService;
	private final MatchService matchService;
	private final DataDragonService dataDragonService;

	private final SummonerRepository summonerRepository;

	public SummonerDto getSummonerData(String summonerName) {
		return summonerService.getSummonerDataByName(summonerName);
	}

	public LeagueDto getLeague(String summonerName){
		String id = summonerService.getSummonerDataByName(summonerName).id();
		return leagueService.getLeagueData(id);
	}

	public List<MatchDto> getMatch(String summonerName) {
		List<String> matchIdList = matchService.getMatchIdList(summonerService.getSummonerDataByName(summonerName).puuid());
		return matchService.getMatchData(matchIdList);
	}

	public SummonerProfileDto getSummonerProfile(String summonerName) {
		//데이터없는 첫 조회시 데이터없는 소환사 데이터 생성
		return summonerRepository.findBySummonerName(summonerName)
				.map(SummonerProfileDto::new)
				.orElseGet(() -> {
					Summoner summoner = summonerService.firstEnroll(summonerName);
					return new SummonerProfileDto(summoner);
				});
	}

	@Transactional
	public SummonerProfileDto resetData(long id) {

		return summonerRepository.findById(id)
				.map(summoner -> {
					SummonerDto newData = summonerService.getSummonerDataById(summoner.getEncryptedId());
					String profileUrl = dataDragonService.getProfileUrl(newData.profileIconId());
					LeagueEntryDto leagueEntryDto = leagueService.getFistLeagueData(summoner.getEncryptedId());

					summoner.resetData(newData, profileUrl, leagueEntryDto);
					return new SummonerProfileDto(summoner);
				})
				.orElseThrow(() -> new ResourceNotFoundException("Summoner not found: " + id));
	}

	public Object getSummonerRankProfile(String summonerName) {
		SummonerDto summonerDto = summonerService.getSummonerDataByName(summonerName);
		LeagueEntryDto leagueEntryDto = leagueService.getFistLeagueData(summonerDto.id());
		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
		String winningRate = NumberUtils.winningRate(leagueEntryDto.wins(),leagueEntryDto.wins()+leagueEntryDto.losses());
		long summonerLevel = summonerDto.summonerLevel();

		return new SummonerProfileDto(leagueEntryDto,profileUrl,winningRate,summonerLevel);
	}

	public TrollScoreDto trollScoreDto(String summonerName) {
		List<String> matchIdList = matchService.getMatchIdList(summonerService.getSummonerDataByName(summonerName).puuid());
		TrollScoreDto trollScoreDto = TrollScoreDto.builder().build();
		return trollScoreDto;
	}
}
