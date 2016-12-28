package com.louis.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.louis.DataPreProcessImpl.DataPreProcessImpl;
import com.louis.IDataPreProcess.IDataPreProcess;
import com.louis.domain.DataBaseUser;
import com.louis.util.JdbcUtil;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年10月16日 下午8:05:53 
 * 类说明 
 */
public class PriceProcess {
	private Connection conn = null;
	private Statement stmt = null;
	private Statement stmt1 = null;
	private ResultSet rs = null;
	private int to;
	int count=0;
	String wholeProvinceCode="";
	String wholeProvinceCodeDe="";
	public void getPriceValues(){
		try {
			String str = "select DataID,productPrice,productPromPrice from data_sept where DataID>=17608885";
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			/*to=stmt.executeUpdate(strAdd);*/
		    rs= stmt.executeQuery(str);
		    System.out.println("rs");
		    while(rs.next()){
		    	count++;
		    	//创建对象
		    	DataBaseUser  dataBaseUser = new DataBaseUser();
		    	IDataPreProcess dataPreProcess=new DataPreProcessImpl();
		    	
		    	dataBaseUser.setDataID(rs.getString(1));
		    	dataBaseUser.setProductPrice(rs.getString(2));
				dataBaseUser.setProductPromPrice(rs.getString(3));
		    	
				System.out.println("-------------------------现在正在处理："+dataBaseUser.getDataID()+"-----------------------"); 
				/*
				 * 规范化商品价格
				 */
				if (dataBaseUser.getProductPrice() != null&& !dataBaseUser.getProductPrice().equals("")) {
					System.out.println("更新价格" + count + ":"+ dataBaseUser.getProductPrice());
					String orignalPrice = dataBaseUser.getProductPrice();
					System.out.println("orignalPrice" + orignalPrice);
					// 得到标准化的价格
					dataBaseUser.setStdProductPrice(dataPreProcess.getStdProductPrice(orignalPrice, "ProductPrice",String.valueOf(count),dataBaseUser.getDataID()));
					System.out.println("stdProductPrice"+ dataBaseUser.getStdProductPrice());
				} else {
					dataBaseUser.setStdProductPrice("");
				}
				
				/*
				 * 
				 * 规范化商品促销价格，使用的是上面的方式
				 */
				if (dataBaseUser.getProductPromPrice() != null&& !dataBaseUser.getProductPromPrice().equals("")) {
					System.out.println("更新促销价格" + count+":"+ dataBaseUser.getProductPromPrice());
					String orignalPromPrice = dataBaseUser.getProductPromPrice();
					// 得到标准化的价格
					dataBaseUser.setStdProductPromPrice(dataPreProcess.getStdProductPrice(orignalPromPrice,"ProductPromPrice", String.valueOf(count),dataBaseUser.getDataID()));
				} else {
					dataBaseUser.setStdProductPromPrice("");
				}
				
				/*
				 * 综合商品价格和商品促销价格，更新价格
				 */
				if (dataBaseUser.getProductPrice() != null&& !dataBaseUser.getProductPrice().equals("")) {
					System.out.println("更新最终价格" + count+":"+ dataBaseUser.getProductPrice());
					String stdPrice = dataBaseUser.getStdProductPrice();
					if (dataBaseUser.getStdProductPromPrice() != null&& !dataBaseUser.getStdProductPromPrice().equals("")) {
						stdPrice = dataBaseUser.getStdProductPromPrice();
					}
					dataBaseUser.setStdPrice(stdPrice);
				
				} else {
					dataBaseUser.setStdPrice("");
				}
				String updateStdProvince = "update data_sept  set std_stdProductPrice='"+ dataBaseUser.getStdProductPrice()
						+ "',std_stdProductPromPrice='"+ dataBaseUser.getStdProductPromPrice()
						+ "',std_stdPrice='" + dataBaseUser.getStdPrice()
						+ "' where DataID='"+ dataBaseUser.getDataID()
						+"'";
				stmt1.executeUpdate(updateStdProvince);
				
				System.out.println("总共执行了"+count+"行:");
				        
		    }
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally{
			JdbcUtil.closeAll(conn, stmt, rs);
		}
	}
}
