package com.example.trollgg.entity;

import com.example.trollgg.dto.match.ParticipantsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MatchPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String summonerName;

    @Column
    private int teamId;

    @Column
    private boolean win;

    @Column
    private int kill;

    @Column
    private int Assist;

    @Column
    private int Death;

    @Column
    private int GoldAttain;

    @Column
    private int VisionScore;

    @Column
    private int dealtToChamp;

    @Column
    private String champion;

    @Column
    private String lane;

    public MatchPlayer(ParticipantsDto participantsDto) {
        this.summonerName = participantsDto.summonerName().replaceAll("\\s+", "").toLowerCase();
        this.teamId = participantsDto.teamId();
        this.win = participantsDto.win();
        this.kill = participantsDto.kills();
        this.Assist = participantsDto.assists();
        this.Death = participantsDto.deaths();
        this.dealtToChamp = participantsDto.totalDamageDealtToChampions();
        this.GoldAttain = participantsDto.goldEarned();
        this.VisionScore = participantsDto.visionScore();
        this.champion = participantsDto.championName();
        this.lane = participantsDto.lane();
    }

}
