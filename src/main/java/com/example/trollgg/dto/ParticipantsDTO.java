package com.example.trollgg.dto;

import java.util.List;

public record ParticipantsDTO(

	String puuid,
	String summonerName,
	int teamId,
	String championName,
	String lane,
	int kills,
	int deaths,
	int assists,
	boolean win,
	List<Teams> teams

) {
}
