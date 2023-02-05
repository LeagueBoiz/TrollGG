package com.example.trollgg.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Summoner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
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
    //데이터 구축후 진행

//    @OneToMany(mappedBy = "summoner")
//    @JsonManagedReference(value = "user-cafe-FK")
//    private List<Match> matchList;
}
