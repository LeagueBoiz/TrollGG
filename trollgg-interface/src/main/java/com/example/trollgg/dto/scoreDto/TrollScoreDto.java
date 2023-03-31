package com.example.trollgg.dto.scoreDto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class TrollScoreDto {
    private String trollRank;
    private Integer combat;
    private Integer sight;
    private Integer assist;
    private Integer total;

    public TrollScoreDto(String trollRank, Integer combat, Integer sight, Integer assist, Integer total) {
        this.trollRank = trollRank;
        this.combat = combat;
        this.sight = sight;
        this.assist = assist;
        this.total = total;
    }
}
