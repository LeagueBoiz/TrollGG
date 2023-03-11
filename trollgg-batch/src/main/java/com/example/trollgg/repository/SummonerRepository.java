package com.example.trollgg.repository;

import com.example.trollgg.entity.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummonerRepository extends JpaRepository<Summoner, Long> {
    Summoner findSummonerBySummonerName(String summonerName);
    Boolean existsSummonerBySummonerName(String summonerName);
}
