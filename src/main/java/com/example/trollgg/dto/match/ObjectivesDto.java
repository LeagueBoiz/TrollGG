package com.example.trollgg.dto.match;

public record ObjectivesDto(
	ChampionDto champion,
	int teamId,
	boolean win

) {
}
