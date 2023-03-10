package com.example.trollgg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.trollgg.dto.match.ParticipantsDto;
import com.example.trollgg.entity.Match;
import com.example.trollgg.entity.MatchPlayer;
import com.example.trollgg.entity.Summoner;
import com.example.trollgg.error.ExternalApiCallException;
import com.example.trollgg.repository.MatchPlayerRepository;
import com.example.trollgg.repository.MatchRepository;
import com.example.trollgg.repository.SummonerRepository;
import com.example.trollgg.util.AverageCalculator;
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

	//==================================================================
	private final RiotApiService riotApiService;
	private final MatchRepository matchRepository;
	private final MatchPlayerRepository matchPlayerRepository;

	private final SummonerRepository summonerRepository;

	//puuid ??? ???????????? 20????????? ???????????? ??????????????????.
	public void saveMatch20(Summoner summoner) {
		//Summoner ???????????? Matchid List??? ???????????? api ??????
		List<String> newmatchIdList = riotApiService.getMatchIdList(summoner.getPuuid());
		//?????? ??????????????? ????????? ??????
		List<Match> currentList = summoner.getMatchList();
		//db??? ?????? ???????????? ????????? ????????????????????????
		filterByDb(summoner, newmatchIdList);
		// ????????? 20???????????? ????????? ????????? ???????????? ??????????????? ??????
		filterByList(newmatchIdList, currentList);
		//????????????????????? ???????????? ?????????
		for (String matchId : newmatchIdList) {
			saveMatch(matchId, summoner);
		}
	}


	//matchid ??? match ???????????? ??????????????????????????????
	public void saveMatch(String matchId, Summoner summoner) {
		//matchId ??? riot api ??????
		MatchDto matchDto = riotApiService.getMatchData(matchId);
		//match ????????? ????????? ???????????????
		Match match = new Match(matchDto, matchId);
		matchRepository.save(match);
		//matchData ?????? ???????????? ???????????? matchData??? ???????????????
		Match findMatch = putPlayersInMatch(matchId, matchDto, match);
		//?????? ???????????? ?????? ???????????? ???????????????
		saveMatchInSummoner(summoner, findMatch);
	}

	private void saveMatchInSummoner(Summoner summoner, Match findMatch) {
		if (summoner.getMatchList().size() >= 20) {
			summoner.getMatchList().remove(0);
		}
		summoner.getMatchList().add(findMatch);
		summonerRepository.save(summoner);
	}

	private Match putPlayersInMatch(String matchId, MatchDto matchDto, Match match) {
		AverageCalculator averageCalculator = new AverageCalculator();
		List<ParticipantsDto> participantsDto = matchDto.info().participants();
		List<MatchPlayer> matchPlayers = new ArrayList<>();
		saveMatchPlayers(match, averageCalculator, participantsDto, matchPlayers);
		Match findMatch = matchRepository.findMatchByMatchId(matchId).orElseThrow(() -> new NullPointerException("????????? ?????????????????????"));
		averageCalculator.getAve();
		findMatch.getAveValue(averageCalculator);
		findMatch.addPlayers(matchPlayers);
		matchRepository.save(findMatch);
		return findMatch;
	}

	private void saveMatchPlayers(Match match, AverageCalculator averageCalculator, List<ParticipantsDto> participantsDto, List<MatchPlayer> matchPlayers) {
		for (ParticipantsDto participant : participantsDto) {
			if (participant.teamId() == 100) {
				averageCalculator.addTeam1(participant);
			} else {
				averageCalculator.addTeam2(participant);
			}
			MatchPlayer matchPlayer = new MatchPlayer(participant);
			matchPlayers.add(matchPlayer);
			matchPlayerRepository.save(matchPlayer);
		}
	}

	private void filterByList(List<String> newmatchIdList, List<Match> currentList) {
		for (Match hasList : currentList) {
			if (newmatchIdList.contains(hasList.getMatchId())) {
				newmatchIdList.remove(hasList.getMatchId());
			}
		}
	}

	private void filterByDb(Summoner summoner, List<String> newmatchIdList) {
		List<String> exists = new ArrayList<>();
		for (String newmatchId : newmatchIdList) {
			if (matchRepository.existsMatchByMatchId(newmatchId)) {
				exists.add(newmatchId);
				Match match = matchRepository.findMatchByMatchId(newmatchId).orElseThrow(() -> new NullPointerException("????????? ?????????????????????"));
				if (!summoner.getMatchList().contains(match)) {
					summoner.getMatchList().add(match);
					summonerRepository.save(summoner);
				}
			}
		}
		for (String exist : exists) {
			newmatchIdList.remove(exist);
		}
	}
}
