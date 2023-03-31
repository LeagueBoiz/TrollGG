package com.example.trollgg.controller;

import com.example.trollgg.dto.SummonerInfoDto;
import com.example.trollgg.dto.SummonerProfileDto;
import com.example.trollgg.dto.riotApi.SummonerDto;
import com.example.trollgg.facade.RiotApiFacade;
import com.example.trollgg.facade.SummonerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class SummonerController {
    private final RiotApiFacade riotAPIFacade;
    private final SummonerFacade summonerFacade;

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
    @GetMapping("/summoners")
    public ResponseEntity<SummonerProfileDto> getSummonerInfo(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotAPIFacade.getSummonerInfo(summonerName));
    }

    /**
     * @param id Summoner pk
     * @return 갱신 데이터
     */
    @PutMapping("/summoners/{id}/renewal-info")
    public ResponseEntity<SummonerInfoDto> renewSummonerInfo(@PathVariable long id) {
        return ResponseEntity.ok().body(summonerFacade.renewSummonerInfo(id));
    }
}
