package com.example.trollgg.dto.riotApi.match;

public record ObjectivesDto(
	ChampionDto champion,
	int teamId,
	boolean win

) {
}
