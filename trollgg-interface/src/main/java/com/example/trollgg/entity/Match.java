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

    @Column
    private int kills;

    @Column
    private int assist;

    @Column
    private int death;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Summoner summoner;
}
