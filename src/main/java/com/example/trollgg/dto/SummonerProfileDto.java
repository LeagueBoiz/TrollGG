package com.example.trollgg.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummonerProfileDto {
    private String summonerName;
    private String profileUrl;
    private String tier;
    private String rank;
    private int wins;
    private int losses;
    private String winningRate;
    public SummonerProfileDto(LeagueEntryDto league, String profileUrl, String winningRate ) {
        this.summonerName = league.summonerName();
        this.profileUrl = profileUrl;
        this.tier = league.tier();
        this.rank = league.rank();
        this.wins = league.wins();
        this.losses = league.losses();
        this.winningRate = winningRate;
    }
}
