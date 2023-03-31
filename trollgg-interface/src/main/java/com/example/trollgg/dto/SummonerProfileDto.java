package com.example.trollgg.dto;

import com.example.trollgg.dto.riotApi.LeagueEntryDto;
import com.example.trollgg.entity.Summoner;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummonerProfileDto {

    private long id;
    private String encryptedId;
    private String summonerName;
    private String profileUrl;
    private String tier;
    private String rank;
    private Integer wins;
    private Integer losses;
    private String winningRate;
    private long summonerLevel;

    public SummonerProfileDto(LeagueEntryDto league, String profileUrl, String winningRate, long summonerLevel) {
        this.summonerName = league.summonerName();
        this.profileUrl = profileUrl;
        this.tier = league.tier();
        this.rank = league.rank();
        this.wins = league.wins();
        this.losses = league.losses();
        this.winningRate = winningRate;
        this.summonerLevel = summonerLevel;
    }

    public SummonerProfileDto(Summoner summoner) {
        this.id = summoner.getId();
        this.encryptedId = summoner.getEncryptedId();
        this.summonerName = summoner.getSummonerName();
        this.profileUrl = summoner.getProfileUrl();
        this.tier = summoner.getTier();
        this.rank = summoner.getRankScore();
        this.wins = summoner.getWin();
        this.losses = summoner.getLoss();
        this.winningRate = summoner.getWinningRate();
        this.summonerLevel = summoner.getSummonerLevel();
    }
}
