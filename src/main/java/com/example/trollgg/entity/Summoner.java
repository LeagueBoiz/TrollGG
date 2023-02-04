package com.example.trollgg.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Summoner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer win;

    @Column
    private Integer loss;

    @Column
    private Integer rankScore;

    @Column
    private String iconImg;
    //데이터 구축후 진행

//    @OneToMany(mappedBy = "summoner")
//    @JsonManagedReference(value = "user-cafe-FK")
//    private List<Match> matchList;
}
