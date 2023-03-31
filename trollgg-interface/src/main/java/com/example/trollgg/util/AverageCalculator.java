package com.example.trollgg.util;

import com.example.trollgg.dto.riotApi.match.ParticipantsDto;
import com.example.trollgg.entity.MatchPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageCalculator {
    int totalKills=0;
    int totalDeath=0;
    int totalAssist=0;
    int totalGoldEarned=0;
    int totalVisionScore=0;
    int totalDealt =0;
    int totalKills2=0;
    int totalDeath2=0;
    int totalAssist2=0;
    int totalGoldEarned2=0;
    int totalVisionScore2=0;
    int totalDealt2 =0;
    float totalKDA =0;
    int totalWin=0;
    float totalScore =0;
    float AveKills = 0;
    float AveDeath = 0;
    float AveAssist = 0;
    int AveVisionScore = 0;
    int AveGoldEarn = 0;
    int  AveDealt =0;
    int AveKills2 = 0;
    int AveDeath2 = 0;
    int AveAssist2 = 0;
    int AveVisionScore2 = 0;
    int AveGoldEarn2 = 0;
    int AveDealt2 =0;
    float AveKDA =0;
    float WinningRate=0;
    float AveScore=0;
    public void addTeam1(ParticipantsDto participantsDto){
        this.totalKills += participantsDto.kills();
        this.totalDeath+=participantsDto.deaths();
        this.totalAssist+=participantsDto.assists();
        this.totalVisionScore+=participantsDto.visionScore();
        this.totalGoldEarned+=participantsDto.goldEarned();
        this.totalDealt+=participantsDto.totalDamageDealtToChampions();
    }
    public void addTeam2(ParticipantsDto participantsDto){
        this.totalKills2 += participantsDto.kills();
        this.totalDeath2+=participantsDto.deaths();
        this.totalAssist2+=participantsDto.assists();
        this.totalVisionScore2+=participantsDto.visionScore();
        this.totalGoldEarned2+=participantsDto.goldEarned();
        this.totalDealt2+=participantsDto.totalDamageDealtToChampions();
    }
    public void getAve(){
        this.AveKills=(float) totalKills/5;
        this.AveDeath=(float) totalDeath/5;
        this.AveAssist=(float) totalAssist/5;
        this.AveVisionScore=totalVisionScore/5;
        this.AveGoldEarn=totalGoldEarned/5;
        this.AveDealt=totalDealt/5;
        this.AveKills2=totalKills2/5;
        this.AveDeath2=totalDeath2/5;
        this.AveAssist2=totalAssist2/5;
        this.AveVisionScore2=totalVisionScore2/5;
        this.AveGoldEarn2=totalGoldEarned2/5;
        this.AveDealt2=totalDealt/5;
    }

    public void addMatchAve(VisonGoldKDA visonGoldKDA, MatchPlayer matchPlayer){
        this.totalKills+= matchPlayer.getKill();
        this.totalDeath+=matchPlayer.getDeath();
        this.totalAssist+=matchPlayer.getAssist();
        this.totalScore +=visonGoldKDA.getDuty();
        if(matchPlayer.isWin()){
            this.totalWin++;
        }
    }

    public void getMatchAve(){
        this.AveKills=(float) totalKills/20;
        this.AveDeath=(float) totalDeath/20;
        this.AveAssist=(float) totalAssist/20;
        this.AveScore = totalScore/20;
        this.WinningRate = (totalWin*100)/(float)20;
    }
}
