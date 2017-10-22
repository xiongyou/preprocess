package com.louis.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月10日 下午4:35:52 
 * 类说明 :Jdbc的连接和关闭
 */
public class JdbcUtil {
	private static String driver = null;
	private static String url = GetUtils.getUrl();
    private static String user = GetUtils.getUserName();
	private static String password = GetUtils.getPassWord();
	
	//连接参数
/*	private static String url = "jdbc:mysql://139.224.112.239:3306/ebmis_db?useSSL=false";
	private static String user = "root";
	private static String password = "1701";*/
	
	/*
	 * 获取数据库的连接
	 * */
	public static Connection getConnection() throws Exception{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("打开链接");
			return DriverManager.getConnection(url, user, password);
			
	}

	/*
	 * 关闭
	 * */
	public static void closeAll(Connection con,Statement stmt,ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
				rs = null;
			}
			if(stmt!=null){
				stmt.close();
				stmt = null;
			}
			if(con!=null){
				con.close();
				con = null;
			}
			System.out.println("关闭连接");
		} catch (Exception e) {
			e.getStackTrace();
		}
	
		
	}
}
