package com.example.trollgg.controller;

import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.dto.SummonerProfileDto;
import com.example.trollgg.facade.RiotApiFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class SummonerController {
    private final RiotApiFacade riotAPIFacade;

    /**
     * @param summonerName 소환사 이름
     * @return 소환사의 id, puuid 등 개인정보
     */
    @GetMapping("/search")
    public ResponseEntity<SummonerDto> searchSummoner(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotAPIFacade.getSummonerData(summonerName));
    }

    /**
     * @param summonerName 소환사 이름
     * @return 소환사 프로필 정보
     */
    @GetMapping("/summoner/profile")
    public ResponseEntity<SummonerProfileDto> getSummonerProfile(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotAPIFacade.getSummonerProfile(summonerName));
    }

    @PostMapping("/summoner/profile/reset")
    public ResponseEntity<Boolean> resetInfo(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotAPIFacade.resetdata(summonerName));
    }
}
