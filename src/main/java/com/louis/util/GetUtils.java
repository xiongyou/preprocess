package com.louis.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Properties;

/*
 * 该类是获取propertie文件信息的类
 * */
public class GetUtils {

	// 获取新表名
	public static String getNewTableName() {

		Properties prop = null;
		try {
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("dbprocess.properties");
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newTableName = prop.getProperty("newTableName");
		return newTableName;
	}

	// 获取旧表名
	public static String getOldTableName() {
		Properties prop = null;
		try {
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("dbprocess.properties");
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String oldTableName = prop.getProperty("oldTableName");
		return oldTableName;
	}

	// 获取最小时间
	public static String getMinTime() {
		Properties prop = null;
		String time = null;
		try {
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("dbprocess.properties");
			prop = new Properties();
			prop.load(in);
			time= prop.getProperty("minTime");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return time;
	}

	// 获取最大时间
	public static String  getMaxTime() {
		Properties prop = null;
		String time = null;
		try {
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("dbprocess.properties");
			prop = new Properties();
			prop.load(in);
			time= prop.getProperty("maxTime");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return time;
	}
	
	// 获取数据库url
	public static String getUrl() {
		Properties prop = null;
		try {
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = prop.getProperty("url");
		return url;
	}

	// 获取数据库用户名
	public static String getUserName() {
		Properties prop = null;
		try {
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String userName = prop.getProperty("username");
		return userName;
	}

	// 获取数据库用户名
	public static String getPassWord() {
		Properties prop = null;
		try {
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
			prop = new Properties();
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String passWord = prop.getProperty("password");
		return passWord;
	}

}
