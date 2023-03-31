package com.example.trollgg.dto.riotApi.match;

import java.util.List;

public record InfoDto(
		String gameMode,
		List<ParticipantsDto> participants,
		long gameDuration,
		long gameStartTimestamp,
		long gameEndTimestamp

) {
}
