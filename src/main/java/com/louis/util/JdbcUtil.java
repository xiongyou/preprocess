package com.louis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月10日 下午4:35:52 
 * 类说明 :Jdbc的连接和关闭
 */
public class JdbcUtil {
	//连接参数
	private static String url = "jdbc:mysql://202.202.5.140:3306/test";
	private static String user = "root";
	private static String password = "1701";
	
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
