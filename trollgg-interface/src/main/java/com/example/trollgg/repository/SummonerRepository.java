package com.example.trollgg.repository;

import com.example.trollgg.entity.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SummonerRepository extends JpaRepository<Summoner, Long> {
    Optional<Summoner> findBySummonerName(String summonerName);
}
