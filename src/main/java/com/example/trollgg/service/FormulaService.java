package com.example.trollgg.service;

import com.example.trollgg.dto.LeagueEntryDto;
import com.example.trollgg.util.NumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FormulaService {
    public String getRankWinningRate(LeagueEntryDto leagueEntryDto) {
        return NumberUtils.winningRate(leagueEntryDto.wins(),leagueEntryDto.wins()+leagueEntryDto.losses());
    }
}
