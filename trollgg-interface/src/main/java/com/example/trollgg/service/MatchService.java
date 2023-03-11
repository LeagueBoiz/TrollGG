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

	//puuid 의 소환사의 20게임을 저장하는 메서드입니다.
	public void saveMatch20(Summoner summoner) {
		//Summoner 기준으로 Matchid List를 가져오는 api 호출
		List<String> newmatchIdList = riotApiService.getMatchIdList(summoner.getPuuid());
		//현재 가지고있는 리스트 조회
		List<Match> currentList = summoner.getMatchList();
		//db에 이미 존재하는 게임은 추가하지않습니다
		filterByDb(summoner, newmatchIdList);
		// 새로운 20게임중에 유저에 등록된 게임들은 리스트에서 삭제
		filterByList(newmatchIdList, currentList);
		//새로추가해야할 게임들만 추가함
		for (String matchId : newmatchIdList) {
			saveMatch(matchId, summoner);
		}
	}


	//matchid 로 match 데이터를 저장하는메서드입니다
	public void saveMatch(String matchId, Summoner summoner) {
		//matchId 로 riot api 호출
		MatchDto matchDto = riotApiService.getMatchData(matchId);
		//match 엔티티 생성후 저장합니다
		Match match = new Match(matchDto, matchId);
		matchRepository.save(match);
		//matchData 안에 유저들을 저장하고 matchData를 변경합니다
		Match findMatch = putPlayersInMatch(matchId, matchDto, match);
		//유저 엔티티에 매치 리스트를 변경합니다
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
		Match findMatch = matchRepository.findMatchByMatchId(matchId).orElseThrow(() -> new NullPointerException("경기를 찾을수없습니다"));
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
				Match match = matchRepository.findMatchByMatchId(newmatchId).orElseThrow(() -> new NullPointerException("경기를 찾을수없습니다"));
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
