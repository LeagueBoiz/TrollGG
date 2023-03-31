package com.example.trollgg.dto;

public record BlueTeam(
        float aveKill,
        float aveAssist,
        float aveDeath,
        Integer aveGoldAttain,
        Integer aveVisionScore,
        Integer aveDealtToChamp
) {
}
