package com.louis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * 时间戳与类之间的转化
 * */
public class DateUtils {
	/* 
     * 将时间转换为时间戳
     */    
    public static long dateToStamp(String s) throws ParseException{
      
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
       /* res = String.valueOf(ts);*/
        return ts;
    }
    
    /* 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
     * 获取最小时间戳
     * */
    public static long getMinTime(String s) {
    	try {
			return dateToStamp(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0L;
    }
    
    /*
     * 获取最小时间戳
     * */
    public static long getMaxTime(String s) {
    	try {
			return dateToStamp(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0L;
    }
}
