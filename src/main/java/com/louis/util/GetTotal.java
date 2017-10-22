package com.louis.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*该类是获取数据库中信息的类*/
public class GetTotal {
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;

	public static int getTotal(String table)  {
		String str = "select count(*) from " + table + " where isOperation=0 ";
		String total = null;
		try {
			connection = JdbcUtil.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(str);
			total = null;
			if (resultSet.next()) {
				total = resultSet.getString(1);
			}

			System.out.println(total);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JdbcUtil.closeAll(connection, statement, resultSet);
		return Integer.parseInt(total);
	}
	
	public static boolean isExist(String table)  {
		try {
			connection = JdbcUtil.getConnection();
			ResultSet rs = connection.getMetaData().getTables(null, null, table, null); 
			if (rs.next()) {
				//有表
				return true;
			}else {
				//无
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("^[0-9]+\\.{0,1}[0-9]*$"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
}
