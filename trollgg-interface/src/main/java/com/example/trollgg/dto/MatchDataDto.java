package com.example.trollgg.dto;

import com.example.trollgg.entity.Match;

import java.util.List;

public record MatchDataDto(
        long id,
        String matchId,
        String gameMode,
        long gameStartTimestamp,
        long gameEndTimeStamp,
        long gameDuration,
        BlueTeam blueTeamAve,
        RedTeam redTeamAve,
        List<Player> players
) {
    public static MatchDataDto of(Match match, List<Player> players) {
        return new MatchDataDto(
                match.getId(),
                match.getMatchId(),
                match.getGameMode(),
                match.getGameStartTimestamp(),
                match.getGameEndTimeStamp(),
                match.getGameDuration(),
                toBlueTeamAve(match.getBlueTeamAve()),
                toRedTeamAve(match.getRedTeamAve()),
                players
        );
    }

    private static BlueTeam toBlueTeamAve(Match.BlueTeamAve blueTeam) {
        return new BlueTeam(
                blueTeam.getAveKill(),
                blueTeam.getAveAssist(),
                blueTeam.getAveDeath(),
                blueTeam.getAveGoldAttain(),
                blueTeam.getAveVisionScore(),
                blueTeam.getAveDealtToChamp()
        );
    }

    private static RedTeam toRedTeamAve(Match.RedTeamAve redTeam) {
        return new RedTeam(
                redTeam.getAveKill2(),
                redTeam.getAveAssist2(),
                redTeam.getAveDeath2(),
                redTeam.getAveGoldAttain2(),
                redTeam.getAveVisionScore2(),
                redTeam.getAveDealtToChamp2()
        );
    }
}
