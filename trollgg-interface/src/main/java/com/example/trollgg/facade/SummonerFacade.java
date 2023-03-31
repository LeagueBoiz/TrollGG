package com.example.trollgg.facade;

import com.example.trollgg.dto.SummonerInfoDto;
import com.example.trollgg.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SummonerFacade {

    private final SummonerService summonerService;

    public SummonerInfoDto renewSummonerInfo(long id) {
        return summonerService.renewSummonerInfo(id);
    }
}
