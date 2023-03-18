package com.example.trollgg.entity;

import com.example.trollgg.dto.match.MatchDto;
import com.example.trollgg.util.AverageCalculator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Match implements Comparable<Match> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unix 타입 시간순 정렬때문에 엔티티에서는 변환하지않았습니다.
    @Column
    private long gameEndTimeStamp;

    @Column
    private String gameMode;

    @Override
    public int compareTo(Match match) {
        if (match.gameEndTimeStamp < gameEndTimeStamp) {
            return 1;
        } else if (match.gameEndTimeStamp > gameEndTimeStamp) {
            return -1;
        }
        return 0;
    }

    @Column
    private String matchId;

    @Column
    private float aveKill;

    @Column
    private float aveAssist;

    @Column
    private float aveDeath;

    @Column
    private Integer aveGoldAttain;

    @Column
    private Integer aveVisionScore;

    @Column
    private Integer aveDealtToChamp;

    @Column
    private float aveKill2;

    @Column
    private float aveAssist2;

    @Column
    private float aveDeath2;

    @Column
    private Integer aveGoldAttain2;

    @Column
    private Integer aveVisionScore2;

    @Column
    private Integer aveDealtToChamp2;

    @Column
    private long gameduration;

    @JoinColumn(name = "summoner_id")
    @ManyToOne
    private Summoner summoner;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "matchplayer_id")
    @JsonManagedReference(value = "matchplayer-match-FK")
    private List<MatchPlayer> matchPlayers = new ArrayList<>();

    public Match(MatchDto matchDto, String matchId) {
        this.gameEndTimeStamp = matchDto.info().gameEndTimestamp();
        this.gameMode = matchDto.info().gameMode();
        this.matchId = matchId;
        this.gameduration = matchDto.info().gameDuration();
    }

    public void getAveValue(AverageCalculator averageCalculator){
        this.aveKill = averageCalculator.getAveKills();
        this.aveAssist = averageCalculator.getAveAssist();
        this.aveDeath = averageCalculator.getAveDeath();
        this.aveGoldAttain = averageCalculator.getAveGoldEarn();
        this.aveVisionScore = averageCalculator.getAveVisionScore();
        this.aveKill2 = averageCalculator.getAveKills2();
        this.aveAssist2 = averageCalculator.getAveAssist2();
        this.aveDeath2 = averageCalculator.getAveDeath2();
        this.aveGoldAttain2 = averageCalculator.getAveGoldEarn2();
        this.aveVisionScore2 = averageCalculator.getAveVisionScore2();
        this.aveDealtToChamp = averageCalculator.getAveDealt();
        this.aveDealtToChamp2 = averageCalculator.getAveDealt2();
    }

    public MatchPlayer findSummoner(String summonerName){
        for(MatchPlayer matchPlayer:this.matchPlayers){
            if(matchPlayer.getSummonerName().equals(summonerName)){
                return matchPlayer;
            }
        }
        return null;
    }

    public void addPlayers(List<MatchPlayer>matchPlayers){
        this.matchPlayers =matchPlayers;
    }
}
