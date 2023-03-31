package com.example.trollgg.service;

import com.example.trollgg.dto.MatchDataDto;
import com.example.trollgg.dto.Player;
import com.example.trollgg.dto.SummonerInfoDto;
import com.example.trollgg.dto.SummonerProfileDto;
import com.example.trollgg.dto.riotApi.LeagueEntryDto;
import com.example.trollgg.dto.riotApi.SummonerDto;
import com.example.trollgg.entity.Match;
import com.example.trollgg.entity.Summoner;
import com.example.trollgg.error.ResourceNotFoundException;
import com.example.trollgg.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

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
	private final LeagueService leagueService;
	private final MatchService matchService;


	public SummonerDto getSummonerDataByName(String summonerName) {
		String url = SUMMONER_DATA_BY_SUMMONER_NAME + summonerName + "?api_key=" + API_KEY;
		return new RestTemplate().getForObject(url, SummonerDto.class);
	}

	public SummonerDto getSummonerDataById(String encryptedId) {
		String url = SUMMONER_DATA_BY_SUMMONER_ID + encryptedId + "?api_key=" + API_KEY;
		return new RestTemplate().getForObject(url, SummonerDto.class);
	}

	private Summoner getById(long id) {
		return summonerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Summoner not found: " + id));
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

	@Transactional
	public SummonerInfoDto renewSummonerInfo(long id) {
		Summoner summoner = getById(id);

		List<Match> updatedMatches = matchService.renewMatch(summoner);
		Summoner updated = renewSummoner(summoner, updatedMatches);

		SummonerProfileDto profile = new SummonerProfileDto(updated);

		List<MatchDataDto> matchDatas = summoner.getMatches().stream()
				.map(match -> {
					List<Player> players = match.getMatchPlayers().stream()
							.map(Player::of)
							.sorted(Comparator.comparing(Player::teamId).thenComparing(Player::lane))
							.toList();

					return MatchDataDto.of(match, players);
				})
				.sorted(Comparator.comparing(MatchDataDto::gameStartTimestamp))
				.limit(20)
				.toList();

		return new SummonerInfoDto(profile, matchDatas);
	}

	private Summoner renewSummoner(Summoner summoner, List<Match> updatedMatches) {
		SummonerDto updatedData = getSummonerDataById(summoner.getEncryptedId());
		LeagueEntryDto leagueEntryDto = leagueService.getFistLeagueData(summoner.getEncryptedId());
		String profileUrl = dataDragonService.getProfileUrl(updatedData.profileIconId());

		summoner.updateData(updatedData, leagueEntryDto, profileUrl);
		summoner.getMatches().addAll(updatedMatches);
		return summonerRepository.save(summoner);
	}
}
