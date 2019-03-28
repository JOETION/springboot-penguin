package com.qexz.util;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/16          FXY        Created
 **********************************************
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公用工具
 */
public class CommonUtils {


    private static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd-hh-mm-ss";

    /**
     * 根据格式返回当前时间
     *
     * @param format
     * @return
     */
    public static String getCurrentTimeByFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }

    /**
     * 按默认<p>yyyy-MM-dd/hh:mm:ss<p/>返回当前时间
     *
     * @return
     */
    public static String geCurrentTime() {
        DateFormat format = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        return format.format(new Date());
    }

}
