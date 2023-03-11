package com.example.trollgg.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "summoner")
public class Summoner {
    @Id
    @Column(name = "member_id")
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
    private List<Match> matchList = new ArrayList<>();

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
        this.accountId =accountId;
    }

    public void resetData(String profileUrl,String winningRate,String tier, String rankScore, Integer win,Integer loss) {
        this.profileUrl =profileUrl;
        this.tier =tier;
        this.rankScore= rankScore;
        this.win = win;
        this.loss = loss;
        this.winningRate = winningRate;
    }
}
