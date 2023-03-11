package com.example.trollgg.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATCH_ID")
    private Long id;

    @Column
    private String matchId;

    @Column
    private String gameMode;

    @OneToMany
    @JoinColumn(name = "SUMMONER_NAME")
    List<Summoner> summonerList;

    @Column
    private int kills;

    @Column
    private int assist;

    @Column
    private int death;
}
