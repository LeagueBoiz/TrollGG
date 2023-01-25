package com.example.trollgg.service.facade;

import org.springframework.stereotype.Service;

import com.example.trollgg.dto.SummonerDto;
import com.example.trollgg.service.SummonerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RiotApiFacade {

	private final SummonerService summonerService;

	public SummonerDto getSummonerData(String summonerName) {
		return summonerService.getSummonerData(summonerName);
	}
}
