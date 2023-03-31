package com.example.trollgg.dto;

import java.util.List;

public record SummonerInfoDto(
        SummonerProfileDto profile,
        List<MatchDataDto> matches
) {
}
