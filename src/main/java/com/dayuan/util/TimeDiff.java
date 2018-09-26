package com.dayuan.util;


public class TimeDiff {

    public long diff(long date1, long date2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        if (date1 < date2) {
            diff = date2 - date1;
        } else {
            diff = date1 - date2;
        }
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return sec;
    }

    //重载
    public long diff(String validateCode_timeStamp) {

        String[] strings = validateCode_timeStamp.split("_");
        long timeStampBegin = Long.parseLong(strings[1]);
        long timeStampNow = System.currentTimeMillis();
        long diff = this.diff(timeStampNow, timeStampBegin);
        return diff;

    }


    public static void main(String[] args) throws Exception {

        long timeStampNow = System.currentTimeMillis();//当前时间戳
        System.out.println(timeStampNow);
        TimeDiff ti = new TimeDiff();
        long ss = ti.diff(1536662757846L, 1536662785546L);
        System.out.println(ss);

    }

}
