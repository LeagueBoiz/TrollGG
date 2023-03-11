package com.example.trollgg.facade;

import com.example.assign.dto.returnDto.ReturnRecord20Dto;
import com.example.assign.entity.Summoner;
import com.example.assign.repository.SummonerRepository;
import com.example.assign.service.DtoService;
import com.example.assign.service.MatchService;
import com.example.assign.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Datafacade {

    private final SummonerService summonerService;

    private final SummonerRepository summonerRepository;

    private final MatchService matchService;

    private final DtoService dtoService;

    public ReturnRecord20Dto get20Data(String summonerName) {
        summonerName.replaceAll("\\s+", "").toLowerCase();
        //db에 Summoner 조회후 summoner 가 없을시 , 즉 첫조회일때 db에 객체 저장
        if (!summonerRepository.existsSummonerBySummonerName(summonerName)) {
            summonerService.firstEnroll(summonerName);
        }
        Summoner summoner = summonerRepository.findSummonerBySummonerName(summonerName).orElseThrow(() -> new NullPointerException("소환사를 찾을수없습니다"));
        //Summoner가 플레이한 20 판의 Match 를 엔티티에 저장
        matchService.saveMatch20(summoner);
        //데이터 가공및 Dto생성
        return dtoService.getReturn20(summoner);
    }
}
