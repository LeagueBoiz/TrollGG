package com.example.trollgg.dto;

import java.util.List;

public record InfoDto(
	String gameMode,
	List<ParticipantsDTO> participants

) {
}
