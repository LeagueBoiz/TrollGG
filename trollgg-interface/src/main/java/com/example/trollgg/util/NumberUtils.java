package com.example.trollgg.util;

public class NumberUtils {
    public static String winningRate(int win, int total) {
        try {
            return String.format("%.2f",(double) win / total * 100);
        } catch (Exception e) {
            throw new IllegalArgumentException("정확한 데이터를 불러오지 못했습니다.");
        }
    }
}
