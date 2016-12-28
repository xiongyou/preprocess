package com.louis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class JudgeMent {
	public static Logger logger = Logger.getLogger("com.foo");
	//将字符串中的数字转换成int类型
	public int judgeInt(String string,String stringName,String stringId){
		logger.setLevel(Level.DEBUG);
		int shuzi =0 ;
		if(string.contains("-1")){
			shuzi = -1;
			return shuzi;
		}else if(string.matches("^[0-9]+$")){
			shuzi = Integer.parseInt(string);
		}else if("".equals(string)||string==null){
			shuzi= -1;//不是正常的数字，更新数据库时为null
		}else{
			shuzi = -1; //不是正常的数字，更新数据库时为null并且保存到日志
			logger.warn(string+"不是数字格式"+"-----"+stringName+"-------"+stringId);
		}
		return shuzi;
	}
	
	//将字符串中的数字转换成float类型
	public float judgeFloat(String string,String stringName,String stringId){
		logger.setLevel(Level.DEBUG);
		float shuzi = 0;
		if(string.matches("^[0-9]+$")||string.matches("^\\d+\\.\\d+$")){
			shuzi = Float.parseFloat(string);
		}else if("".equals(string)||string==null||string.matches("[\\u4e00-\\u9fa5]+")){
			shuzi= -1;//不是正常的数字，更新数据库时为null
		}else{
			shuzi = -1; //不是正常的数字，更新数据库时为null并且保存到日志
			logger.warn(string+"不是数字格式"+"-----"+stringName+"-------"+stringId);
		}
		return shuzi;
	}
	//转化字符串是null或者是“”的格式
	public String updateNull(String name,String value){
		String str = "";
		if("".equals(value)||value==null){
			 str = name+"=null";
		}else{
			 str = name+"='"+value+"'";
		}
		return str;
	}
	//重载 int
	public String updateNull(String name,int value){
		String str = "";
		if(value==-1){
			 str = name+"=null";
		}else{
			 str = name+"='"+value+"'";
		}
		return str;
	}
	//重载 float
	public String updateNull(String name,float value){
		String str = "";
		if(value==-1){
			 str = name+"=null";
		}else{
			 str = name+"='"+value+"'";
		}
		return str;
	}
	
	//更新storeLocationCode\productLocationCode
	public int getLocationCode(int provinceCode,int cityCode){
		int locationCode=-1;
		if(cityCode!=-1){
			locationCode = cityCode;
		}else if(cityCode==-1&&provinceCode!=-1){
			locationCode = provinceCode;
		}
		return locationCode;
	}
    public static Date parse(String str, String pattern, Locale locale) {
        if(str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public static String format(Date date, String pattern, Locale locale) {
        if(date == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern, locale).format(date);
    }
	
}
