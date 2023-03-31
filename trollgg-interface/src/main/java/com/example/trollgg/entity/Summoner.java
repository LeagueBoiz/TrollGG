package com.example.trollgg.entity;

import com.example.trollgg.dto.riotApi.LeagueEntryDto;
import com.example.trollgg.dto.riotApi.SummonerDto;
import com.example.trollgg.util.NumberUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "summoner")
public class Summoner extends BaseTimeEntity {
    @Id
    @Column(name = "summoner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "SUMMONER_NAME")
    private String summonerName;

    @Column
    private String profileUrl;

    @Column
    private String tier;

    @Column
    private String rankScore;

    @Column
    private Integer win;

    @Column
    private Integer loss;

    @Column
    private String winningRate;

    @Column
    private String puuid;

    @Column
    private long summonerLevel;

    @Column
    private String encryptedId;

    @Column
    private String accountId;

    @BatchSize(size = 30)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "summoner_match",
            joinColumns = @JoinColumn(name = "summoner_id"),
            inverseJoinColumns = @JoinColumn(name = "match_id"))
    private List<Match> matches = new ArrayList<>();

    @Builder
    public Summoner(String summonerName, String profileUrl, String puuid, long summonerLevel, String encryptedId, String accountId) {
        Assert.hasText(summonerName, "summonerName must not be empty");
        Assert.hasText(accountId, "accountId must not be empty");
        Assert.hasText(encryptedId, "encryptedId must not be empty");
        this.summonerName = summonerName;
        this.profileUrl = profileUrl;
        this.puuid = puuid;
        this.summonerLevel = summonerLevel;
        this.encryptedId = encryptedId;
        this.accountId = accountId;
    }

    public void updateData(SummonerDto summoner, LeagueEntryDto leagueEntryDto, String profileUrl) {
        this.summonerName = summoner.name();
        this.summonerLevel = summoner.summonerLevel();
        this.tier = leagueEntryDto.tier();
        this.rankScore = leagueEntryDto.rank();
        this.win = leagueEntryDto.wins();
        this.loss = leagueEntryDto.losses();
        this.winningRate = getWinningRate(leagueEntryDto.wins(), leagueEntryDto.losses());
        this.profileUrl = profileUrl;
    }

    public void updateMatches(List<Match> matches) {
        this.matches = matches;
    }

    private String getWinningRate(int wins, int losses) {
        return NumberUtils.winningRate(wins, losses);
    }
}
