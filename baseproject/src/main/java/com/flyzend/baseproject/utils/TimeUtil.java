package com.flyzend.baseproject.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * 王灿
 */
public class TimeUtil {
    /**
     * 将GMT 时间转换成年月日或者月日（如果是当年则显示月日）
     * 即Tue May 31 17:46:55 +0800 2011   2011-3-11
     * @param timeGMT
     * @return
     */
    public static String formatGMTStr(String timeGMT){
        int currentYear;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy",Locale.US);
        try {
            Date date =sdf.parse(timeGMT);
            Calendar calendar = Calendar.getInstance();
            currentYear = calendar.get(Calendar.YEAR);
            calendar.setTime(date);
            //去年甚至更久以前
            if (calendar.get(Calendar.YEAR) != currentYear){
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(calendar.getTime());
            }else{
                sdf = new SimpleDateFormat("MM-dd");
                return sdf.format(calendar.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     * @param timestampString 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return nowTimeStamp
     */
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time / 1000);
        return nowTimeStamp;
    }
}
