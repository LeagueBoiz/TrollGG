package com.example.trollgg.entity;

import com.example.trollgg.dto.riotApi.match.MatchDto;
import com.example.trollgg.util.AverageCalculator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Match extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String matchId;

    @Column
    private long gameStartTimestamp;

    @Column
    private long gameEndTimeStamp;

    @Column
    private String gameMode;

    @Column
    private long gameDuration;

    @Embedded
    BlueTeamAve blueTeamAve;

    @Embedded
    RedTeamAve redTeamAve;

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    private List<MatchPlayer> matchPlayers = new ArrayList<>();

    @Embeddable
    @EqualsAndHashCode
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlueTeamAve {
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

        public BlueTeamAve(AverageCalculator averageCalculator) {
            this.aveKill = averageCalculator.getAveKills();
            this.aveAssist = averageCalculator.getAveAssist();
            this.aveDeath = averageCalculator.getAveDeath();
            this.aveGoldAttain = averageCalculator.getAveGoldEarn();
            this.aveVisionScore = averageCalculator.getAveVisionScore();
            this.aveDealtToChamp = averageCalculator.getAveDealt();
        }
    }

    @Embeddable
    @EqualsAndHashCode
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedTeamAve {
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

        public RedTeamAve(AverageCalculator averageCalculator) {
            this.aveKill2 = averageCalculator.getAveKills2();
            this.aveAssist2 = averageCalculator.getAveAssist2();
            this.aveDeath2 = averageCalculator.getAveDeath2();
            this.aveGoldAttain2 = averageCalculator.getAveGoldEarn2();
            this.aveVisionScore2 = averageCalculator.getAveVisionScore2();
            this.aveDealtToChamp2 = averageCalculator.getAveDealt2();
        }
    }

    public Match(MatchDto matchDto) {
        this.matchId = matchDto.metadata().matchId();
        this.gameMode = matchDto.info().gameMode();
        this.gameStartTimestamp = matchDto.info().gameStartTimestamp();
        this.gameEndTimeStamp = matchDto.info().gameEndTimestamp();
        this.gameDuration = matchDto.info().gameDuration();
    }

    public Match setAveValueOfMatch(BlueTeamAve blue, RedTeamAve red) {
        this.blueTeamAve = blue;
        this.redTeamAve = red;

        return this;
    }
}