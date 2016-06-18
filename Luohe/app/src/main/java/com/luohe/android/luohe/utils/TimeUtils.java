package com.luohe.android.luohe.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;

/**
 * Author: GanQuan Date: 16/5/22 Email:ganquan3640@gmail.com
 */

public class TimeUtils {
    public static String getFormatTime(String data) {
        if (TextUtils.isEmpty(data))
            return "";
        long timeStamp;
        try {
            timeStamp = Long.valueOf(data);
        } catch (Throwable throwable) {
            timeStamp = 0L;
        }
        if (timeStamp == 0L) {// format data
            return StringUtils.friendly_time(data);

        } else {// is time stamp

            return getFormatTime(System.currentTimeMillis(), timeStamp);
        }
    }

    /**
     * 根据时间差值获取时间区间，正值表示以后，负值表示以前，以以前为例（单位：秒）： 0到-60：刚刚 -60到-3600：?分钟前
     * -3600到-86400：？小时前 ... 超过半年直接格式化。
     *
     * @param currentTime 当前时间(毫秒)
     * @param postTime    发布时间（秒）
     * @return 时间区间描述
     */
    private static String getFormatTime(long currentTime, long postTime) {
        long time = currentTime / 1000 - postTime / 1000;
        StringBuffer timeBuffer = new StringBuffer();
        if (time < 60)
            timeBuffer.append("刚刚");
        else if (time >= 60 && time < 3600)
            timeBuffer.append(time / 60).append("分钟前");
        else if (time >= 3600 && time < 86400)
            timeBuffer.append(time / 3600).append("小时前");
        else if (time >= 86400 && time < 2592000){
            if (time>=86400 && time<(86400*2))
            timeBuffer.append("昨天");
            else
                timeBuffer.append(time / 86400).append("天前");
        }
        else if (time >= 2592000 && time < 15552000)
            timeBuffer.append(time / 2592000).append("个月前");
        else
            timeBuffer.append(new SimpleDateFormat("yyyy-MM-dd").format(postTime * 1000));

        return timeBuffer.toString();
    }
}
