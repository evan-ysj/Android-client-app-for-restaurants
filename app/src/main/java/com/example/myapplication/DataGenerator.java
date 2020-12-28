package com.example.myapplication;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.myapplication.db.entity.ReserveHistoryEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static Random random = new Random();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<ReserveHistoryEntity> generateReservations() {
        List<ReserveHistoryEntity> reservations = new ArrayList<>();
        for(int i = 0; i < 10; ++i) {
            ReserveHistoryEntity reservation = new ReserveHistoryEntity();
            reservation.setDate(randomDateTime(-3, 3));
            reservation.setExpired(true);
            reservation.setNo_of_guests(random.nextInt(10));
            reservation.setRsv_number(i + 1);
            reservations.add(reservation);
        }
        return reservations;
    }

    /**
     * 取范围日期的随机日期时间,不含边界
     * @param startDay
     * @param endDay
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime randomLocalDateTime(int startDay, int endDay){

        int plusMinus = 1;
        if(startDay < 0 && endDay > 0){
            plusMinus = Math.random()>0.5?1:-1;
            if(plusMinus>0){
                startDay = 0;
            }else{
                endDay = Math.abs(startDay);
                startDay = 0;
            }
        }else if(startDay < 0 && endDay < 0){
            plusMinus = -1;

            //两个数交换
            startDay = startDay + endDay;
            endDay  = startDay - endDay;
            startDay = startDay -endDay;

            //取绝对值
            startDay = Math.abs(startDay);
            endDay = Math.abs(endDay);

        }

        LocalDate day = LocalDate.now().plusDays(plusMinus * (random.nextInt(endDay) + startDay));
        int hour = random.nextInt(23) + 1;
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        LocalTime time = LocalTime.of(hour, minute, second);
        return LocalDateTime.of(day, time);
    }

    /**
     * 取范围日期的随机日期时间,不含边界
     * @param startDay
     * @param endDay
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Date randomDateTime(int startDay, int endDay){
        LocalDateTime ldt = randomLocalDateTime(startDay,endDay);
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }
}
