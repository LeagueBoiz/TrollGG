package com.example.trollgg.dto.riotApi;

public record SummonerDto(
	String id,
	String accountId,
	String puuid,
	String name,
	int profileIconId,
	long revisionDate,
	long summonerLevel

) {
}
