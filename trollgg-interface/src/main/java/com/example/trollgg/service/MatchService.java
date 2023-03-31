package com.example.trollgg.service;

import com.example.trollgg.dto.riotApi.match.MatchDto;
import com.example.trollgg.entity.Match;
import com.example.trollgg.entity.MatchPlayer;
import com.example.trollgg.entity.Summoner;
import com.example.trollgg.error.ExternalApiCallException;
import com.example.trollgg.repository.MatchPlayerRepository;
import com.example.trollgg.repository.MatchRepository;
import com.example.trollgg.util.AverageCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchPlayerRepository matchPlayerRepository;

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

    public MatchDto getMatchData(String matchId) {
        try {
            String url = MATCH_DATA_RIOT_URL + matchId + "?api_key=" + API_KEY;
            return new RestTemplate()
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<MatchDto>() {
                    })
                    .getBody();
        } catch (Exception e) {
            throw new ExternalApiCallException(e.getMessage());
        }
    }

    public List<MatchDto> getMatchDatas(List<String> matchIdList) {
        return matchIdList.stream()
                .map(id -> {
                    try {
                        String url = MATCH_DATA_RIOT_URL + id + "?api_key=" + API_KEY;
                        return new RestTemplate()
                                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<MatchDto>() {
                                })
                                .getBody();
                    } catch (Exception e) {
                        throw new ExternalApiCallException(e.getMessage());
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Transactional
    public List<Match> renewMatch(Summoner summoner) {
        return getMatchIdList(summoner.getPuuid()).stream()
                .map(matchId -> matchRepository.findByMatchId(matchId)
                        .orElseGet(() -> saveMatchV2(matchId))
                )
                .toList();
    }

    public Match saveMatchV2(String matchId) {
        MatchDto matchData = getMatchData(matchId);
        Match match = new Match(matchData);
        Match saved = matchRepository.save(match);

        AverageCalculator avgMatchData = saveMatchPlayerAndCalculateAvg(matchData, saved);
        Match.BlueTeamAve blueTeamAve = new Match.BlueTeamAve(avgMatchData);
        Match.RedTeamAve redTeamAve = new Match.RedTeamAve(avgMatchData);

        return matchRepository.save(saved.setAveValueOfMatch(blueTeamAve, redTeamAve));
    }

    private AverageCalculator saveMatchPlayerAndCalculateAvg(MatchDto matchData, Match saved) {
        AverageCalculator avgCalculator = new AverageCalculator();

        matchData.info().participants()
                .forEach(participant -> {
                    switch (participant.teamId()) {
                        case 100:
                            avgCalculator.addTeam1(participant);
                        case 200:
                            avgCalculator.addTeam2(participant);
                    }

                    MatchPlayer player = new MatchPlayer(participant, saved);
                    matchPlayerRepository.save(player);
                });

        avgCalculator.getAve();
        return avgCalculator;
    }
}
