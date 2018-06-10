package com.example.zyq.kaminotetest.Class;

/**
 * Created by zyq on 2018/2/21.
 * 来源：CSDN或简书，作者已忘，向原作者致歉
 */
import java.util.Calendar;

public class MyDate {

    Calendar calendar = Calendar.getInstance();

    //获取年
    private final int year = calendar.get(Calendar.YEAR);

    // 获取月，这里需要需要月份的范围为0~11，因此获取月份的时候需要+1才是当前月份值
    private final int month = calendar.get(Calendar.MONTH) + 1;

    // 获取日
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);

    // 获取时
//    private final int hour = calendar.get(Calendar.HOUR);
    private final int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24小时表示

    // 获取分
    private final int minute = calendar.get(Calendar.MINUTE);

    // 获取秒
    private final int second = calendar.get(Calendar.SECOND);

    // 星期，英语国家星期从星期日开始计算
    private final int weekday = calendar.get(Calendar.DAY_OF_WEEK);

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getWeekday() {
        return weekday;
    }

    @Override
    public String toString() {
        //        return year + "年" + month + "月" + day + "日 " + hour + ":" + minute + ":" + second;
//        return String.format("%ty年%tm月%td日 %td %tT %tZ", year, month, day, hour, minute, second);
        return String.format("%02d年%02d月%02d日 %02d:%02d:%02d", year, month, day, hour, minute, second);

    }

    public String getDate() {
        return String.format("%02d年%02d月%02d日", year, month, day);
    }

    public String getYearAndMonth() {
        return String.format("%02d.%02d", year, month);
    }


//    public void setCalendar() {
//        calendar = Calendar.getInstance();
//    }

//    // 基本用法，获取年月日时分秒星期
//    public void getCreateDate() {
//
//        // 获取年
//        int year = calendar.get(Calendar.YEAR);
//
//        // 获取月，这里需要需要月份的范围为0~11，因此获取月份的时候需要+1才是当前月份值
//        int month = calendar.get(Calendar.MONTH) + 1;
//
//        // 获取日
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        // 获取时
//        int hour = calendar.get(Calendar.HOUR);
//        // int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24小时表示
//
//        // 获取分
//        int minute = calendar.get(Calendar.MINUTE);
//
//        // 获取秒
//        int second = calendar.get(Calendar.SECOND);
//
//        // 星期，英语国家星期从星期日开始计算
//        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
//
//
//        //return year + "年" + month + "月" + day + "日 " + hour + ":" + minute + ":" + second + "星期" + weekday;
//        //System.out.println("现在是" + year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分" + second + "秒" + "星期" + weekday);
//    }
//
//
//    // 设置日期
//    public void setCreateDate(Calendar calendar) {
//
//        calendar.set(Calendar.YEAR, 2000);
//        System.out.println("现在是" + calendar.get(Calendar.YEAR) + "年");
//
//        calendar.set(2008, 8, 8);
//        // 获取年
//        int year = calendar.get(Calendar.YEAR);
//
//        // 获取月
//        int month = calendar.get(Calendar.MONTH);
//
//        // 获取日
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        System.out.println("现在是" + year + "年" + month + "月" + day + "日");
//    }


}
