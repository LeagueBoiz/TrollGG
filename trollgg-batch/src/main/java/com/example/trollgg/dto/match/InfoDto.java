package com.example.trollgg.dto.match;

import java.util.List;

public record InfoDto(
	String gameMode,
	List<ParticipantsDto> participants

) {
}
