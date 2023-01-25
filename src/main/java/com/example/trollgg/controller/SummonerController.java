package com.example.trollgg.controller;

import com.example.trollgg.dto.LeagueDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.facade.RiotApiFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SummonerController {

    private final RiotApiFacade riotAPIFacade;

    @GetMapping("/search")
    public ResponseEntity<SummonerDto> searchSummoner(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotAPIFacade.getSummonerData(summonerName));
    }

    @GetMapping("/league")
    public ResponseEntity<LeagueDto> LeagueData(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotAPIFacade.getLeague(summonerName));
    }
}
