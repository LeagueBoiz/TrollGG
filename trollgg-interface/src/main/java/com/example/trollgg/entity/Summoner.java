package com.example.trollgg.entity;

import com.example.trollgg.dto.LeagueEntryDto;
import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.util.NumberUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "summoner")
public class Summoner {
    @Id
    @Column(name = "summoner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "summoner")
    private List<Match> matchList;

    @Builder
    public Summoner(String summonerName, String profileUrl, String puuid, long summonerLevel, String encryptedId,String accountId) {
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

    public void resetData(SummonerDto summoner, String profileUrl, LeagueEntryDto leagueEntryDto) {
        this.summonerName = summoner.name();
        this.profileUrl = profileUrl;
        this.tier = leagueEntryDto.tier();
        this.rankScore = leagueEntryDto.rank();
        this.win = leagueEntryDto.wins();
        this.loss = leagueEntryDto.losses();
        this.winningRate = getWinningRate(leagueEntryDto.wins(), leagueEntryDto.losses());
        this.summonerLevel = summoner.summonerLevel();
    }

    private String getWinningRate(int wins, int losses) {
        return NumberUtils.winningRate(wins, losses);
    }
}
