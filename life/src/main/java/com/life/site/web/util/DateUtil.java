package com.life.site.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Date : 2020. 7. 22.
 * @package : com.hansol.util
 * @file : NumberUtil.java
 * @Author : PSJ
 * @version : 1.0
 *
 * ===============================================
 *  수정내역
 * ===============================================
 * DATE         AUTHOR         NOTE
 * -----------------------------------------------
 * 2020. 7. 22.      PSJ        최초 생성
 *
 */
public class DateUtil {
    
    /**
     * 문자열 날짜 포맷 변환.
     * 
     * @param date
     * @param fromFormatString
     * @param toFormatString
     * @return
     */
    public static String exFormattedDate(String date, String fromFormatString, String toFormatString) {
        SimpleDateFormat fromFormat = new SimpleDateFormat(fromFormatString);
        SimpleDateFormat toFormat = new SimpleDateFormat(toFormatString);
        Date fromDate = null;

        try {
            fromDate = fromFormat.parse(date);
        } catch (ParseException e) {
            return "";
        }
        return toFormat.format(fromDate);
    }

    /**
     * 현재 날짜+시간 얻기
     * 
     * yyyy : 년도 w : 일년안에서 몇번째 주인지 W : 한달안에서 몇번째 주인지 MM : 월 dd : 일 D : 일년에서 몇번째 일인지
     * E : 요일 (ex) Tuesday; Tue F : 요일을 숫자로 (ex) 2 hh : 시간 ( 12시간 단위로 1-12) kk : 시간
     * (12시간 단위로 0-11) HH : 시간 (24시간 단위로 1-24) KK : 시간 (24시간 단위로 0-23) a : PM 인지 AM
     * 인지 mm : 분 ss : 초. second in minute SSS : Millisecond zzzz : General time zone
     * (ex) Pacific Standard Time; PST; GMT-08:00 Z : RFC 822 time zone (ex) -800
     * 
     * @param formatStr
     * @return
     */
    public static String getDayTimeNow(String formatStr) {
        Date today = new Date();
        SimpleDateFormat headFormat = new SimpleDateFormat(formatStr);

        return headFormat.format(today);
    }

    /**
     * 어제 날짜 얻기
     * 
     * @param formatStr
     * @return
     */
    public static String getDayBefore(String formatStr) {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, -1);
        String beforeDate = new java.text.SimpleDateFormat(formatStr).format(day.getTime());
        return beforeDate;
    }
    
    public static String getDateTime() {
        String currentDateTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
        currentDateTime = dateFormat.format(new Date());
        
        return currentDateTime;
    }
    
    public static String getDay() {
        String currentDay = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.KOREA);
        currentDay = dateFormat.format(new Date());
        
        return currentDay;
    }
    
    public static String getYyyymm() {
        String currentDay = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM", Locale.KOREA);
        currentDay = dateFormat.format(new Date());
        
        return currentDay;
    }
    
    public static String getYyyymmdd() {
        String currentDay = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        currentDay = dateFormat.format(new Date());
        
        return currentDay;
    }
    
    public static String getYyyy() {
        String currentDay = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        currentDay = dateFormat.format(new Date());
        
        return currentDay;
    }
    
    public static String getMm() {
        String currentDay = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM", Locale.KOREA);
        currentDay = dateFormat.format(new Date());
        
        return currentDay;
    }
    
    public static String insertDateTimeMarke(String data) {
        String insertData = "";
        insertData += data.substring(0, 4) + ".";
        insertData += data.substring(4, 6) + ".";
        insertData += data.substring(6, 8) + " ";
        insertData += data.substring(8, 10) + ":";
        insertData += data.substring(10, 12) + ":";
        insertData += data.substring(12, 14) + "";
        return insertData;
    }
    
    public static String insertDateMinMarke(String _data) {
        String insertData = "";
        String mark = ".";
        String data = (String) _data;
        
        if (data != null && (data.trim()).length() >= 12) {
            insertData += data.substring(0, 4) + mark;
            insertData += data.substring(4, 6) + mark;
            insertData += data.substring(6, 8) + mark;
            insertData += data.substring(8, 10) + mark;
            insertData += data.substring(10, 12) + mark;
        } else {
            insertData = "";
        }
        return insertData;
    }
    
    public static String insertDateMarke(Object _data) {
        String insertData = "";
        String mark = "-";
        String data = (String) _data;
        
        if (data != null && (data.trim()).length() >= 8) {
            insertData += data.substring(0, 4) + mark;
            insertData += data.substring(4, 6) + mark;
            insertData += data.substring(6, 8);
        } else {
            insertData = "";
        }
        return insertData;
    }
    
    public static String insertDateMarkeTime(Object _data, String _time) {
        String insertData = "";
        String mark = "-";
        String data = (String) _data;
        
        if (data != null && (data.trim()).length() >= 8) {
            insertData += data.substring(0, 4) + mark;
            insertData += data.substring(4, 6) + mark;
            insertData += data.substring(6, 8);
            if (_time != null) {
                insertData += " " + _time;
            }
            
        } else {
            insertData = "";
        }
        return insertData;
    }
    
    public static String insertDateMarke(String data, String mark) {
        String insertData = "";
        
        if (data != null && (data.trim()).length() >= 8) {
            insertData += data.substring(0, 4) + mark;
            insertData += data.substring(4, 6) + mark;
            insertData += data.substring(6, 8);
        }
        return insertData;
    }
    
    public static String getAfterMonth(String _yyyymm, int _cnt) {
        Date date = null;
        String afterYyyymm = null;
        Calendar calendar = Calendar.getInstance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM", Locale.KOREA);
        try {
            date = dateFormat.parse(_yyyymm);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
            return "";
        }
        
        calendar.setTime(date);
        
        calendar.add(Calendar.MONTH, _cnt);
        afterYyyymm = dateFormat.format(calendar.getTime());
        
        return afterYyyymm;
    }
    
    /**
     * 몇일 전 날짜 가져오기
     * @param _yyyymmdd 기준년월일
     * @param _cnt 날짜 수
     * @return 계산한 날짜의 년월일
     */
    public static String getPreDay(Object _yyyymmdd, Object _cnt) {
        Date date = null;
        String preDd = null;
        String yyyymmdd = (String) _yyyymmdd;
        int cnt = Integer.parseInt(String.valueOf(_cnt));
        
        Calendar calendar = Calendar.getInstance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        try {
            date = dateFormat.parse(yyyymmdd);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
            return "";
        }
        
        calendar.setTime(date);
        
        calendar.add(Calendar.DATE, cnt);
        preDd = dateFormat.format(calendar.getTime());
        
        return preDd;
    }
    
    public static String getPreMonth(Object _yyyymm, Object _cnt) {
        Date date = null;
        String preMm = null;
        String yyyymm = (String) _yyyymm;
        int cnt = Integer.parseInt((String) _cnt);
        Calendar calendar = Calendar.getInstance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM", Locale.KOREA);
        try {
            date = dateFormat.parse(yyyymm);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
            return "";
        }
        
        calendar.setTime(date);
        
        calendar.add(Calendar.MONTH, cnt);
        preMm = dateFormat.format(calendar.getTime());
        
        return preMm;
    }
    
    public static String getPreYear(Object _yyyy, int cnt) {
        Date date = null;
        String preYyyy = null;
        String yyyy = (String) _yyyy;
        
        Calendar calendar = Calendar.getInstance();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        try {
            date = dateFormat.parse(yyyy);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
            return "";
        }
        
        calendar.setTime(date);
        
        calendar.add(Calendar.YEAR, cnt);
        preYyyy = dateFormat.format(calendar.getTime());
        
        return preYyyy;
    }
    
    /*
     * 두 날짜 사이의 일수 구하기 Ex) int cnt = DateUtil.getDayDiff("2013-01-01",
     * "2013-09-20");
     */
    public static int getDayDiff(String f_yyyymmdd, String t_yyyymmdd) {
        Date f_date = null;
        Date t_date = null;
//        Calendar f_calendar = Calendar.getInstance();
//        Calendar t_calendar = Calendar.getInstance();
        long fLong , tLong;
        int dayCnt = 0;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        
        try {
            f_date = dateFormat.parse(f_yyyymmdd);
            t_date = dateFormat.parse(t_yyyymmdd);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
            return 0;
        }
        
        fLong = f_date.getTime();
        tLong = t_date.getTime();
        
        dayCnt = (int) ((tLong - fLong) / (1000 * 60 * 60 * 24));
        
        return dayCnt;
    }
    
    /**
     * 시작일부터 종료일까지 사이의 날짜를 배열에 담아 리턴 ( 시작일과 종료일을 모두 포함한다 )
     * 
     * @param fromDate yyyy-MM-dd 형식의 시작일
     * @param toDate   yyyy-MM-dd 형식의 종료일
     * @return yyyy-MM-dd 형식의 날짜가 담긴 배열
     */
    public static String[] getDiffDays(String fromDate, String toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Calendar cal = Calendar.getInstance();
        
        try {
            cal.setTime(sdf.parse(fromDate));
        } catch (Exception e) {
        }
        
        int count = getDayDiff(fromDate, toDate);
        
        // 시작일부터
        cal.add(Calendar.DATE, -1);
        
        // 데이터 저장
        ArrayList<String> list = new ArrayList<String>();
        
        for (int i = 0; i <= count; i++) {
            cal.add(Calendar.DATE, 1);
            
            list.add(sdf.format(cal.getTime()));
        }
        
        String[] result = new String[list.size()];
        
        list.toArray(result);
        
        return result;
    }
    
    /**
     * 날짜의 차이(초단위) 반환
     * 
     * @return 차이값
     */
    public static long getDiffCurSecond(String sTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date FirstDate;
        Date currentTime = new Date();
        try {
            FirstDate = format.parse(sTime);
            long calDate = FirstDate.getTime() - currentTime.getTime();
            calDate /= 1000;
            return Math.abs(calDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.MAX_VALUE;
        }
    }
}
