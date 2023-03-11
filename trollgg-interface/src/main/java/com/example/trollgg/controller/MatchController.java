package com.example.trollgg.controller;

import com.example.trollgg.dto.match.MatchDto;
import com.example.trollgg.facade.RiotApiFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MatchController {
    private final RiotApiFacade riotApiFacade;

    /**
     * @param summonerName 소환사 이름
     * @return 소환사의 최근 20게임에 대한 게임 별 상세정보
     */
    @GetMapping("/match")
    public ResponseEntity<List<MatchDto>> matchData(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotApiFacade.getMatch(summonerName));
    }

    @GetMapping("/match/score")
    public ResponseEntity<List<MatchDto>> scores(@RequestParam(value = "title") String summonerName) {
        return ResponseEntity.ok(riotApiFacade.getMatch(summonerName));
    }
}
