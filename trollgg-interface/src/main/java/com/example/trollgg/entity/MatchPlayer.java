package com.example.trollgg.entity;

import com.example.trollgg.dto.riotApi.match.ParticipantsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MatchPlayer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String summonerName;

    @Column
    private String puuid;

    @Column
    private int teamId;

    @Column
    private boolean win;

    @Column
    private int kill;

    @Column
    private int assist;

    @Column
    private int death;

    @Column
    private int goldAttain;

    @Column
    private int visionScore;

    @Column
    private int dealtToChamp;

    @Column
    private String champion;

    @Column
    private String lane;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    public MatchPlayer(ParticipantsDto participantsDto, Match saved) {
        this.puuid = participantsDto.puuid();
        this.summonerName = StringUtils.normalizeSpace(participantsDto.summonerName());
        this.teamId = participantsDto.teamId();
        this.win = participantsDto.win();
        this.kill = participantsDto.kills();
        this.assist = participantsDto.assists();
        this.death = participantsDto.deaths();
        this.dealtToChamp = participantsDto.totalDamageDealtToChampions();
        this.goldAttain = participantsDto.goldEarned();
        this.visionScore = participantsDto.visionScore();
        this.champion = participantsDto.championName();
        this.lane = participantsDto.lane();
        this.match = saved;
    }
}
