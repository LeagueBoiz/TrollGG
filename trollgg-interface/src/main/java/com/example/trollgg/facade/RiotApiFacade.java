package com.example.trollgg.facade;

import com.example.trollgg.dto.LeagueDto;
import com.example.trollgg.dto.LeagueEntryDto;
import com.example.trollgg.dto.ScoreDto.TrollScoreDto;
import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.dto.SummonerProfileDto;
import com.example.trollgg.dto.match.MatchDto;
import com.example.trollgg.entity.Match;
import com.example.trollgg.entity.Summoner;
import com.example.trollgg.repository.SummonerRepository;
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

	private final SummonerRepository summonerRepository;

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
		//데이터없는 첫 조회시 데이터없는 소환사 데이터 생성
		if (!summonerRepository.existsSummonerBySummonerName(summonerName)) {
			summonerService.firstEnroll(summonerName);
		}
		Summoner summoner = summonerRepository.findSummonerBySummonerName(summonerName);

		String summonerName1 = summoner.getSummonerName();

		String profileUrl1 = summoner.getProfileUrl();

		String tier = summoner.getTier();

		String rank = summoner.getRankScore();

		Integer wins = summoner.getWin();

		Integer loss = summoner.getLoss();

		String winningRate1 = summoner.getWinningRate();

		long summonerLevel = summoner.getSummonerLevel();

		SummonerProfileDto summonerProfileDto = SummonerProfileDto.builder()
				.summonerName(summonerName1)
				.profileUrl(profileUrl1)
				.tier(tier)
				.rank(rank)
				.wins(wins)
				.losses(loss)
				.winningRate(winningRate1)
				.summonerLevel(summonerLevel)
				.build();

		return summonerProfileDto;
	}

	public boolean resetData(String summonerName){

		SummonerDto summonerDto = summonerService.getSummonerData(summonerName);
		Summoner summoner = summonerRepository.findSummonerBySummonerName(summonerName);
		LeagueEntryDto leagueEntryDto =leagueService.getFistLeagueData(summoner.getEncryptedId());
		List<String> matchIdList = matchService.getMatchIdList(summoner.getPuuid());
		for(String matchId: matchIdList){

		}

		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
		String winningRate = NumberUtils.winningRate(leagueEntryDto.wins(),leagueEntryDto.wins()+leagueEntryDto.losses());
		String tier = leagueEntryDto.tier();
		String rankScore = leagueEntryDto.rank();
		Integer win = leagueEntryDto.wins();
		Integer loss = leagueEntryDto.losses();
		summoner.resetData(profileUrl,winningRate,tier,rankScore,win,loss);
		summonerRepository.save(summoner);
		return true;
	}

	public Object getSummonerRankProfile(String summonerName) {
		SummonerDto summonerDto = summonerService.getSummonerData(summonerName);
		LeagueEntryDto leagueEntryDto =leagueService.getFistLeagueData(summonerDto.id());
		String profileUrl = dataDragonService.getProfileUrl(summonerDto.profileIconId());
		String winningRate = NumberUtils.winningRate(leagueEntryDto.wins(),leagueEntryDto.wins()+leagueEntryDto.losses());
		long summonerLevel = summonerDto.summonerLevel();

		return new SummonerProfileDto(leagueEntryDto,profileUrl,winningRate,summonerLevel);
	}

	public TrollScoreDto trollScoreDto(String summonerName) {
		List<String> matchIdList = matchService.getMatchIdList(summonerService.getSummonerData(summonerName).puuid());
		TrollScoreDto trollScoreDto = TrollScoreDto.builder().build();
		return trollScoreDto;
	}
}
