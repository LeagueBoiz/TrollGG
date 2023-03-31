//package com.example.trollgg.util;
//
//import com.example.trollgg.entity.Match;
//import com.example.trollgg.entity.MatchPlayer;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RatingCalculator {
//
//    public VisonGoldKDA rating(Match match, MatchPlayer matchPlayer) {
//        int team = matchPlayer.getTeamId();
//        VisonGoldKDA visonGoldKDA = new VisonGoldKDA();
//        visonGoldKDA.setKDAScore(KDAScore(match, matchPlayer, team));
//        visonGoldKDA.setGrowthScore(ScoreMachine(matchPlayer.getGoldAttain(), match.getAveGoldAttain(), match.getAveGoldAttain2(), team, (float) 2.5));
//        visonGoldKDA.setVisionScore(ScoreMachine(matchPlayer.getVisionScore(), match.getAveVisionScore(), match.getAveVisionScore2(), team, (float) 1.3));
//        visonGoldKDA.setCombatScore(ScoreMachine(matchPlayer.getDealtToChamp(),match.getAveDealtToChamp(),2,100,(float) 2.5));
//        visonGoldKDA.setDuty(totalDuty(visonGoldKDA.KDAScore, visonGoldKDA.VisionScore, visonGoldKDA.growthScore, visonGoldKDA.combatScore));
//        return visonGoldKDA;
//    }
//
//    public int KDAScore(Match match, MatchPlayer matchPlayer, int team) {
//        float hisKDA = (matchPlayer.getKill() + matchPlayer.getAssist()) / (float) matchPlayer.getDeath();
//        float teamKDA;
//        if (team == 100) {
//            teamKDA = (match.getAveKill() + match.getAveAssist()) / (float) match.getAveDeath();
//        } else {
//            teamKDA = (match.getAveKill2() + match.getAveAssist2()) / (float) match.getAveDeath2();
//        }
//        float Score = Sigmoid(hisKDA / teamKDA, (float) 1.8);
//        return (int) Score;
//    }
//
//    public int ScoreMachine(int playerValue, int teamvalue1, int teamvalue2, int team, float curve) {
//        float playerAmount = (float) playerValue;
//        float teamAmount;
//        if (team == 100) {
//            teamAmount = (float) teamvalue1;
//        } else {
//            teamAmount = (float) teamvalue2;
//        }
//        float Score = Sigmoid(playerAmount / teamAmount, curve);
//        return (int) Score;
//    }
//
//    public float totalDuty(int KDAScore, int VisionScore, int GoldScore,int combatScore ) {
//        if(VisionScore==0){
//            VisionScore=50;
//        }
//        if(combatScore==0){
//            combatScore=50;
//        }
//        float avepoint = (float) (KDAScore + VisionScore + GoldScore+combatScore) / 4;
//        return TotalSigmoid(avepoint, (float) 0.035);
//    }
//
//    public float Sigmoid(float x, float k) {
//        float y = (float) (100 * (1 / (1 + Math.pow((Math.E), -(x - 1) * k))));
//        return y;
//    }
//
//    public float TotalSigmoid(float x, float k) {
//        float y = (float) (6 * (1 / (1 + Math.pow((Math.E), -(x - 50) * k))) - 2);
//        return y;
//    }
//}
