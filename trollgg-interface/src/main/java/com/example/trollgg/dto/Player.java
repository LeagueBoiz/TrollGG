package com.example.trollgg.dto;

import com.example.trollgg.entity.MatchPlayer;

public record Player(
        long id,
        String puuid,
        String summonerName,
        int teamId,
        boolean win,
        int kill,
        int Assist,
        int Death,
        int GoldAttain,
        int VisionScore,
        int dealtToChamp,
        String champion,
        String lane
) {
    public static Player of(MatchPlayer matchPlayer) {
        return new Player(
                matchPlayer.getId(),
                matchPlayer.getPuuid(),
                matchPlayer.getSummonerName(),
                matchPlayer.getTeamId(),
                matchPlayer.isWin(),
                matchPlayer.getKill(),
                matchPlayer.getAssist(),
                matchPlayer.getDeath(),
                matchPlayer.getGoldAttain(),
                matchPlayer.getVisionScore(),
                matchPlayer.getDealtToChamp(),
                matchPlayer.getChampion(),
                matchPlayer.getLane()
        );
    }
}
