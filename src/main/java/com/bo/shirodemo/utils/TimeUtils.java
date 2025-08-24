package com.bo.shirodemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 时间工具类
 * @Author yb zheng
 * @Date 2025/7/8 10:19
 * @Version 1.0
 */

public class TimeUtils {

    public static String Date2String(Date date){
        SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return format3.format(date);
        }
        return null;
    }

}
