package com.example.trollgg.facade;

import org.springframework.stereotype.Component;

import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.service.SummonerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RiotApiFacade {

	private final SummonerService summonerService;

	public SummonerDto getSummonerData(String summonerName) {
		return summonerService.getSummonerData(summonerName);
	}
}
