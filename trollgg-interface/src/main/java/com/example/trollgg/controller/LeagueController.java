package com.example.trollgg.controller;

import com.example.trollgg.dto.riotApi.LeagueDto;
import com.example.trollgg.facade.RiotApiFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LeagueController {
    private final RiotApiFacade riotApiFacade;

    /**
     * @param summonerName 소환사 이름
     * @return 소환사의 전반적인 전적 프로필 정보
     */
    @GetMapping("/league")
    public ResponseEntity<LeagueDto> leagueData(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotApiFacade.getLeague(summonerName));
    }
}
