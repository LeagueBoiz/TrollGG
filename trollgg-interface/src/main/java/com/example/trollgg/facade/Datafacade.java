package com.example.trollgg.facade;


import com.example.trollgg.repository.SummonerRepository;
import com.example.trollgg.service.MatchService;
import com.example.trollgg.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Datafacade {

    private final SummonerService summonerService;

    private final SummonerRepository summonerRepository;

    private final MatchService matchService;

//    private final DtoService dtoService;
//
//    public ReturnRecord20Dto get20Data(String summonerName) {
//        summonerName.replaceAll("\\s+", "").toLowerCase();
//        Summoner summoner = summonerRepository.findSummonerBySummonerName(summonerName).orElseThrow(() -> new NullPointerException("소환사를 찾을수없습니다"));
//        //Summoner가 플레이한 20 판의 Match 를 엔티티에 저장
//        matchService.saveMatch20(summoner);
//        //데이터 가공및 Dto생성
//        return dtoService.getReturn20(summoner);
//    }
}
