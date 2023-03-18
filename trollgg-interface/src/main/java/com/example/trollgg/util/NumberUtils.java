package com.example.trollgg.util;

public class NumberUtils {
    public static String winningRate(int wins, int losses) {
        try {
            int total = wins + losses;
            return String.format("%.2f", (double) wins / total * 100);
        } catch (Exception e) {
            throw new IllegalArgumentException("정확한 데이터를 불러오지 못했습니다.");
        }
    }
}
