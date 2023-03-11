package com.example.trollgg.util;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeCalculator {
    //Unix 데이터를 시간및 날짜로 바꿔줍니다
    public  String getTimestampToDate(long timestamp){
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    // 초를 x분 x초 라는 문자열로 바꿔줍니다
    public String getTimefromSec(long sec){
        long minutes = sec / 60;
        long remainingSeconds = sec % 60;
        String value =minutes + "분 " + remainingSeconds + "초";
        return value;
    }
}
