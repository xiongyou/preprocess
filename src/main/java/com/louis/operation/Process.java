package com.louis.operation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.louis.DataPreProcessImpl.DataPreProcessImpl;
import com.louis.IDataPreProcess.IDataPreProcess;
import com.louis.domain.DataBaseUser;
import com.louis.util.ExtractFromExcel;
import com.louis.util.JdbcUtil;
import com.louis.util.JudgeMent;

import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormat;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.omg.CORBA.DATA_CONVERSION;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月10日 下午9:09:00 
 * 类说明 :遍历数据库，将数据库中的值一一调用相应的函数进行规范化
 */
public  class Process {
	//新表的名字
	String newTableName = "data11_new";
	String oldTableName = "data11";
	String errorProductName = "errorproduct";
	//处理的表名
	String table=newTableName;
	private Connection conn = null;
	private Statement stmt = null;
	private Statement stmt1 = null;
	private ResultSet rs = null;
	private Date extractTime;
	int count=0;
	String wholeProvinceCode="";
	String wholeProvinceCodeDe="";
	//得到标准省市划分
	ExtractFromExcel extractFromExcel = new ExtractFromExcel();	
	//设置处理数据当前月份
	private String YEAR = "2016";

	public static Logger logger = Logger.getLogger("com.foo");
	/*更新到另外一个表中*/
	public void newTable(){
		int i;
		
		
		String strNewTable = "insert  into "+newTableName+" SELECT * from "+oldTableName+" where dataID in(select max(dataID) from "+oldTableName+" where errorInfo='' or errorInfo is null group BY productInnerId)";
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			i = stmt.executeUpdate(strNewTable);
			System.out.println(i);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	
	}
	/*将带有错误信息的数据更新到另外一个表中*/
	public void errorTable(){
		int i;
		
		String strErrorTable ="insert  into errorproduct(productInnerId,errorInfo,extractTime)  SELECT (productInnerId,errorInfo,extractTime) from "+oldTableName+" where dataID in(select max(dataID) from "+oldTableName+" where errorInfo<>'' and errorInfo is not null group BY productInnerId";
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			i = stmt.executeUpdate(strErrorTable);
			System.out.println(i);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	
	}
	/*获取数据库中相应的值*/
	@Test
	public void getValues(){
		
		//查询数据库中的相应的值
		try {
			List<HashMap<String,String>> regionCodeName = extractFromExcel.getRegionFromExcel();
	/*//添加字段
		    String strAdd = "alter table data add ("
		    		+ "std_stdProductPrice decimal(15,3) null,"
		    		+ "std_stdProductPromPrice decimal(15,3) null,"
		    		+ "std_stdPrice decimal(15,3) null,"
		    		+ "std_province decimal(15,3) null,"
		    		+ "std_provinceCode varchar(255) null,"
		    		+ "std_city varchar(255) null,"
		    		+ "std_cityCode int(11) null,"
		    		+ "std_deliveryProvince varchar(255) null,"
		    		+ "std_deliveryProvinceCode int(11)  null,"
		    		+ "std_deliveryCity varchar(255) null,"
		    		+ "std_deliveryCityCode int(11)  null,"
		    		+ "std_storeLocationProvince varchar(255) null,"
		    		+ "std_storeLocationProvinceCode int(11) null,"
		    		+ "std_storeLocationCity varchar(255) null,"
		    		+ "std_storeLocationCityCode int(11)  null,"
		    		+ "std_stdWeightValue decimal(15,3) null)";*/
			//where DataID>=17506833    ID:17515251
			String str = "select dataID,productName,province,city,deliveryStartArea,storeLocation,productPrice,productPromPrice,weight,storeURL,extractTime from "+table+" where isOperation=0";
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
	    /*	to=stmt.executeUpdate(strAdd);*/
		    rs= stmt.executeQuery(str);
		    System.out.println("rs");
		    while(rs.next()){
		    	count++;
		    	//创建对象
		    	DataBaseUser  dataBaseUser = new DataBaseUser();
		    	IDataPreProcess dataPreProcess=new DataPreProcessImpl();
		    	/*
		    	 * 得到数据库中每一行的值
		    	 * */
		    	dataBaseUser.setDataID(rs.getString(1));
		    	dataBaseUser.setProductName(rs.getString(2));
		    	dataBaseUser.setProvince(rs.getString(3));
		    	dataBaseUser.setCity(rs.getString(4));
		    	dataBaseUser.setDeliveryStartArea(rs.getString(5));
		    	dataBaseUser.setStoreLocation(rs.getString(6));
			    dataBaseUser.setProductPrice(rs.getString(7));
			    dataBaseUser.setProductPromPrice(rs.getString(8));
			    dataBaseUser.setWeight(rs.getString(9));
			    dataBaseUser.setStoreURL(rs.getString(10));
			    extractTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(11));
				//得到long类型当前时间
				long l = System.currentTimeMillis();
				//new日期对象
				Date date = new Date(l);
				//转换提日期输出格式
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    System.out.println("---------------现在正在处理："+count+"行----------ID:"+dataBaseUser.getDataID()+"---------------"+"时间:"+dateFormat.format(date)+"-----------------------");   
		   
			    
			   /*  * 
			     * province规范化：首先判断不是空。调用getStdProvince(),调用 dataBaseUser的setStdProvince()
			     * */
			    System.out.println("******分割线******");
			    System.out.println("更新省份："+dataBaseUser.getProvince());
			    if(dataBaseUser.getProvince()!=null&&!dataBaseUser.getProvince().equals("")&&!dataBaseUser.getProvince().equals("其他")){
		        	
		        	HashMap<String,String> stdProvince = dataPreProcess.getStdProvince(dataBaseUser.getProvince(),"orignalProvince",String.valueOf(count),dataBaseUser.getDataID(),regionCodeName);
		        	Set<Entry<String, String>> sets = stdProvince.entrySet();
		        	for (Entry<String, String> entry : sets) {
		        		//获取到的标准地名和标准编码
						String regionName = entry.getValue();
						String regionCode = entry.getKey();
						wholeProvinceCode = regionCode;
			        	if(regionName!=null&&regionCode!=null){
			        		dataBaseUser.setStdProvince(regionName);
			        		dataBaseUser.setStdProvinceCode(regionCode);
			        	}	
		        	}
			    }else {
			    	dataBaseUser.setStdProvince("");
			    	dataBaseUser.setStdProvinceCode("");
			    }
			    System.out.println("省份："+dataBaseUser.getStdProvince()+"编号:"+dataBaseUser.getStdProvinceCode());

		       /*  
		          * 
		          * city规范化：首先判断不是空，调用getStdCity()
		          * */ 
			   System.out.println("******分割线******");
			   System.out.println("更新城市："+dataBaseUser.getCity());
		       if(dataBaseUser.getCity()!=null&&!dataBaseUser.getCity().equals("")&&!dataBaseUser.getCity().equals("其他")){
		        	
		        	
		        	HashMap<String,String> stdCity = dataPreProcess.getStdCity(dataBaseUser.getCity(),"orignalCity",String.valueOf(count),wholeProvinceCode,dataBaseUser.getDataID(),regionCodeName);
		         	Set<Entry<String, String>> sets = stdCity.entrySet();
		        	for (Entry<String, String> entry : sets) {
		        		//获取到的标准地名和标准编码
						String regionName = entry.getValue();
						String regionCode = entry.getKey();
						
			        	if(regionName!=null&&regionCode!=null){
			        		dataBaseUser.setStdCity(regionName);
			        		dataBaseUser.setStdCityCode(regionCode);
			        	}	
		        	}
		        }else {
		        	dataBaseUser.setStdCity("");
	        		dataBaseUser.setStdCityCode("");
		        }     
			    /*----------------------------------这里根据city去补充还是空的province----------------------------------------*/
			    if("".equals(dataBaseUser.getStdProvinceCode())){
			    	if(!"".equals(dataBaseUser.getStdCityCode())){
			    		int stdCityCodeNull = Integer.parseInt(dataBaseUser.getStdCityCode());
			    		int stdProvincCodeNull = (stdCityCodeNull/10000)*10000;
			    		System.out.println(stdProvincCodeNull);
			    		String stdProviceNull = "";
			    		HashMap<String, String> mapNull = new HashMap<>();
			    		int isbreak = 0;
			    		for (int i = 0; i < regionCodeName.size(); i++) {
							mapNull = regionCodeName.get(i);
							Iterator<Entry<String, String>> itor = mapNull.entrySet().iterator();
							while(itor.hasNext()){
								Entry<String, String> entry = itor.next();
								if(entry.getKey().equals(String.valueOf(stdProvincCodeNull))){
									stdProviceNull = mapNull.get(String.valueOf(stdProvincCodeNull));
						    		System.out.println(stdProviceNull);
						    		dataBaseUser.setStdProvince(stdProviceNull);
							    	dataBaseUser.setStdProvinceCode(String.valueOf(stdProvincCodeNull));
							    	isbreak = 1;
							    	break;
								}
							}
							if(isbreak == 1){
								break;
							}
				    
						}

			    	}
			    } 
			    System.out.println("城市"+dataBaseUser.getStdCity()+"编号："+dataBaseUser.getStdCityCode());
		       
			  /*  * 
			    * 规范化deliveryStartArea
			    * */
			    System.out.println("******分割线******");
			    System.out.println("更新发货地："+dataBaseUser.getDeliveryStartArea());
		        if(dataBaseUser.getDeliveryStartArea()!=null&&!dataBaseUser.getDeliveryStartArea().equals("")&&!dataBaseUser.getDeliveryStartArea().equals("其他")){
		        	
		        	List<HashMap<String, String>> list = dataPreProcess.getStdDelivery(dataBaseUser.getDeliveryStartArea(), "deliveryStartArea",String.valueOf(count),dataBaseUser.getDataID(),regionCodeName);
		        	//使用上面的方法，这里穿参数是为了能够错误的输出
		        	HashMap<String,String> stdDeliveryProvince= list.get(0);
		        	HashMap<String,String> stdDeliveryCity= list.get(1);
		         	Set<Entry<String, String>> sets = stdDeliveryProvince.entrySet();
		         	Set<Entry<String, String>> sets2 = stdDeliveryCity.entrySet();
		         	//province
		        	for (Entry<String, String> entry : sets) {
		        		//获取到的标准地名和标准编码
						String regionName = entry.getValue();
						String regionCode = entry.getKey();
						wholeProvinceCodeDe = regionCode;
						dataBaseUser.setStdDeliveryProvince(regionName);
						dataBaseUser.setStdDeliveryProvinceCode(regionCode);

		        	}
		        	//city
		        	for (Entry<String, String> entry : sets2) {
		        		//获取到的标准地名和标准编码
						String regionName = entry.getValue();
						String regionCode = entry.getKey();
						dataBaseUser.setStdDeliveryCity(regionName);
						dataBaseUser.setStdDeliveryCityCode(regionCode);
			        		
		        	}
		        }else {
		        	dataBaseUser.setStdDeliveryProvince("");
					dataBaseUser.setStdDeliveryProvinceCode("");
		        	dataBaseUser.setStdDeliveryCity("");
					dataBaseUser.setStdDeliveryCityCode("");
		        }
			    
		        /*----------------------------------这里根据Deliverycity去补充还是空的province----------------------------------------*/
			    if("".equals(dataBaseUser.getStdDeliveryProvinceCode())){
			    	if(!"".equals(dataBaseUser.getStdDeliveryCityCode())){
			    		int stdCityCodeNull = Integer.parseInt(dataBaseUser.getStdDeliveryCityCode());
			    		int stdProvincCodeNull = (stdCityCodeNull/10000)*10000;
			    		System.out.println(stdProvincCodeNull);
			    		String stdProviceNull = "";
			    		HashMap<String, String> mapNull = new HashMap<>();
			    		int isbreak = 0;
			    		for (int i = 0; i < regionCodeName.size(); i++) {
							mapNull = regionCodeName.get(i);
							Iterator<Entry<String, String>> itor = mapNull.entrySet().iterator();
							while(itor.hasNext()){
								Entry<String, String> entry = itor.next();
								if(entry.getKey().equals(String.valueOf(stdProvincCodeNull))){
									stdProviceNull = mapNull.get(String.valueOf(stdProvincCodeNull));
						    		System.out.println(stdProviceNull);
						    		dataBaseUser.setStdDeliveryProvince(stdProviceNull);
							    	dataBaseUser.setStdDeliveryProvinceCode(String.valueOf(stdProvincCodeNull));
							    	isbreak = 1;
							    	break;
								}
							}
							if(isbreak == 1){
								break;
							}
				    
						}

			    	}
			    } 
		        
		        /* * 
		         * 规范化storeLocation
		         * */
			    System.out.println("******分割线******");
			    System.out.println("更新店铺地址："+dataBaseUser.getStoreLocation());
		    //判断：如果StoreLocation为空的话，就吧DeliveryStartArea赋值给StoreLocatio
    		    if((dataBaseUser.getStoreLocation()==null||"".equals(dataBaseUser.getStoreLocation()))&&dataBaseUser.getDeliveryStartArea()!=null&&!"".equals(dataBaseUser.getDeliveryStartArea())){
			    	/*System.out.println("更新店铺地址1："+dataBaseUser.getStoreLocation());*/
			    	dataBaseUser.setStdStoreLocationProvince(dataBaseUser.getStdDeliveryProvince());
					dataBaseUser.setStdStoreLocationProvinceCode(dataBaseUser.getStdDeliveryProvinceCode());
			    	dataBaseUser.setStdStoreLocationCity(dataBaseUser.getStdDeliveryCity());
					dataBaseUser.setStdStoreLocationCityCode(dataBaseUser.getStdDeliveryCityCode());  
			    	
			    }else  if(dataBaseUser.getStoreLocation()!=null&&!"".equals(dataBaseUser.getStoreLocation())){
		        	/*System.out.println("更新店铺地址2："+dataBaseUser.getStoreLocation());*/
		        	//deliveryStartArea与storeLocation实现是一样的，所以就直接使用了getStdDelivery()
		        	List<HashMap<String, String>> list = dataPreProcess.getStdDelivery(dataBaseUser.getStoreLocation(), "storeLocation",String.valueOf(count),dataBaseUser.getDataID(),regionCodeName);
		        	//使用上面的方法，这里穿参数是为了能够错误的输出
		        	HashMap<String,String> stdStoreLocationProvince= list.get(0);
		        	HashMap<String,String> stdStoreLocationCity= list.get(1);
		         	Set<Entry<String, String>> sets = stdStoreLocationProvince.entrySet();
		         	Set<Entry<String, String>> sets2 = stdStoreLocationCity.entrySet();
		         	//province
		        	for (Entry<String, String> entry : sets) {
		        		//获取到的标准地名和标准编码
						String regionName = entry.getValue();
						String regionCode = entry.getKey();
						dataBaseUser.setStdStoreLocationProvince(regionName);
						dataBaseUser.setStdStoreLocationProvinceCode(regionCode);

		        	}
		        	//city
		        	for (Entry<String, String> entry : sets2) {
		        		//获取到的标准地名和标准编码
						String regionName = entry.getValue();
						String regionCode = entry.getKey();
						dataBaseUser.setStdStoreLocationCity(regionName);
						dataBaseUser.setStdStoreLocationCityCode(regionCode);
			        		
		        	}
		        }else{
		        	dataBaseUser.setStdStoreLocationProvince("");
					dataBaseUser.setStdStoreLocationProvinceCode("");
		        	dataBaseUser.setStdStoreLocationCity("");
					dataBaseUser.setStdStoreLocationCityCode("");
		        }
    		    
    		    /*----------------------------------这里根据StoreLocation去补充还是空的province----------------------------------------*/
			    if("".equals(dataBaseUser.getStdStoreLocationProvinceCode())){
			    	if(!"".equals(dataBaseUser.getStdStoreLocationCityCode())){
			    		int stdCityCodeNull = Integer.parseInt(dataBaseUser.getStdStoreLocationCityCode());
			    		int stdProvincCodeNull = (stdCityCodeNull/10000)*10000;
			    		System.out.println(stdProvincCodeNull);
			    		String stdProviceNull = "";
			    		HashMap<String, String> mapNull = new HashMap<>();
			    		int isbreak = 0;
			    		for (int i = 0; i < regionCodeName.size(); i++) {
							mapNull = regionCodeName.get(i);
							Iterator<Entry<String, String>> itor = mapNull.entrySet().iterator();
							while(itor.hasNext()){
								Entry<String, String> entry = itor.next();
								if(entry.getKey().equals(String.valueOf(stdProvincCodeNull))){
									stdProviceNull = mapNull.get(String.valueOf(stdProvincCodeNull));
						    		System.out.println(stdProviceNull);
						    		dataBaseUser.setStdStoreLocationProvince(stdProviceNull);
							    	dataBaseUser.setStdStoreLocationProvinceCode(String.valueOf(stdProvincCodeNull));
							    	isbreak = 1;
							    	break;
								}
							}
							if(isbreak == 1){
								break;
							}
				    
						}

			    	}
			    } 
			    System.out.println("省份："+dataBaseUser.getStdStoreLocationProvince()+"省份编号:"+dataBaseUser.getStdStoreLocationProvinceCode()+"城市："+dataBaseUser.getStdStoreLocationCity()+"城市编号："+dataBaseUser.getStdStoreLocationCityCode());
		        
		         /** 规范化单位:如果单位是kg，ml全部化成g
		         * */
			    System.out.println("******分割线******");
			    System.out.println("更新单位:"+dataBaseUser.getWeight());
			    try {
			    	 if(dataBaseUser.getWeight()!=null&&!dataBaseUser.getWeight().equals("")){
					    	
					    	String orignalWeight = dataBaseUser.getWeight();
					    	
					    	//得到标准的质量数值
					    	String weight = dataPreProcess.getStdWeightValue(orignalWeight, "weight",String.valueOf(count),dataBaseUser.getDataID());
					    	if(weight.contains("*")){
					    		if(DataPreProcessImpl.countXX(weight)==1){
					    			String str1 = weight.split("\\*")[0];
									String str2 = weight.split("\\*")[1];
									String reg = "[^0-9]";
									Pattern p = Pattern.compile(reg);
									Matcher m1 = p.matcher(str1);
									Matcher m2 = p.matcher(str2);
									weight =String.valueOf( Float.parseFloat(m1.replaceAll("").trim())*Float.parseFloat(m2.replaceAll("").trim()));
					    		}
					    		if(DataPreProcessImpl.countXX(weight)==2){
					    			String str1 = weight.split("\\*")[0];
									String str2 = weight.split("\\*")[1];
									String str3 = weight.split("\\*")[2];
									String reg = "[^0-9]";
									Pattern p = Pattern.compile(reg);
									Matcher m1 = p.matcher(str1);
									Matcher m2 = p.matcher(str2);
									Matcher m3 = p.matcher(str3);
									weight =String.valueOf( Float.parseFloat(m1.replaceAll("").trim())*Float.parseFloat(m2.replaceAll("").trim())*Float.parseFloat(m3.replaceAll("").trim()));
					    		}
								
							}else if(weight.contains("x")||weight.contains("X")){
								weight = weight.toLowerCase();
								if(DataPreProcessImpl.countX(weight)==1){
									String str1 = weight.split("x")[0];
									String str2 = weight.split("x")[1];
									String reg = "[^0-9]";
									Pattern p = Pattern.compile(reg);
									Matcher m1 = p.matcher(str1);
									Matcher m2 = p.matcher(str2);
									weight = String.valueOf( Float.parseFloat(m1.replaceAll("").trim())*Float.parseFloat(m2.replaceAll("").trim()));
								}
								if(DataPreProcessImpl.countX(weight)==2){
									String str1 = weight.split("x")[0];
									String str2 = weight.split("x")[1];
									String str3 = weight.split("x")[2];
									String reg = "[^0-9]";
									Pattern p = Pattern.compile(reg);
									Matcher m1 = p.matcher(str1);
									Matcher m2 = p.matcher(str2);
									Matcher m3 = p.matcher(str3);
									weight = String.valueOf( Float.parseFloat(m1.replaceAll("").trim())*Float.parseFloat(m2.replaceAll("").trim())*Float.parseFloat(m3.replaceAll("").trim()));
								}
							}
					    	/*处理stdWightValue中特殊的*/
					    	Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
						    Matcher m = p.matcher(weight);
							
							if(weight.matches(".*[a-zA-z].*")||m.find()||weight.contains("-")){
								Pattern patternCutData = Pattern.compile("\\d+");
								Matcher matcher = patternCutData.matcher(weight);
								while (matcher.find()) {
									weight = matcher.group(0);
									break;
								}
							}
					    	dataBaseUser.setStdWeightValue(weight);
					    }else{
					    	dataBaseUser.setStdWeightValue("");
					    }
			    	 if(Float.parseFloat(dataBaseUser.getStdWeightValue())>99999999.99){
			    		 dataBaseUser.setStdWeightValue("");
			    	 }
			    	 //处理24</b>.80
			    	
			    	 if(dataBaseUser.getStdWeightValue().toLowerCase().contains("<")){
			    		 String bBugStr = dataBaseUser.getStdWeightValue().toLowerCase();
			    		 String bBug = bBugStr.substring(0, bBugStr.indexOf("<"))+bBugStr.substring(bBugStr.indexOf("."), bBugStr.length());
			    		 dataBaseUser.setStdWeightValue(bBug);
			    		 System.out.println("............."+bBug);
			    	 }
			    	  System.out.println("数值:"+dataBaseUser.getStdWeightValue());	
				} catch (Exception e) {
					
				}
			   
			          
		       /*  
		        *  规范化商品价格
		        * */
			    System.out.println("******分割线******");
			    System.out.println("更新价格"+":"+dataBaseUser.getProductPrice());
			   if(dataBaseUser.getProductPrice()!=null&&!dataBaseUser.getProductPrice().equals("")){
			    	
			    	String orignalPrice = dataBaseUser.getProductPrice();
			    	//得到标准化的价格
			    	dataBaseUser.setStdProductPrice(dataPreProcess.getStdProductPrice(orignalPrice,"ProductPrice",String.valueOf(count),dataBaseUser.getDataID()));
			   	 //处理24</b>.80
			    	
			    	 if(dataBaseUser.getStdProductPrice().toLowerCase().contains("<")){
			    		 String bBugStr = dataBaseUser.getStdProductPrice().toLowerCase();
			    		 String bBug = bBugStr.substring(0, bBugStr.indexOf("<"))+bBugStr.substring(bBugStr.indexOf("."), bBugStr.length());
			    		 dataBaseUser.setStdProductPrice(bBug);
			    		 System.out.println("............."+bBug);
			    	 }
			    	System.out.println("stdProductPrice"+dataBaseUser.getStdProductPrice());
			    }else{
			    	dataBaseUser.setStdProductPrice("");
			    }
			   System.out.println("价格:"+dataBaseUser.getStdProductPrice());
			 /*  
			  * 
			  * 规范化商品促销价格，使用的是上面的方式
			  * */
			   System.out.println("******分割线******");
			   System.out.println("更新促销价格:"+dataBaseUser.getProductPromPrice());
			   if(dataBaseUser.getProductPromPrice()!=null&&!dataBaseUser.getProductPromPrice().equals("")){
			    	
			    	String orignalPromPrice = dataBaseUser.getProductPromPrice();
			    	//得到标准化的价格
			    	dataBaseUser.setStdProductPromPrice(dataPreProcess.getStdProductPrice(orignalPromPrice,"ProductPromPrice",String.valueOf(count),dataBaseUser.getDataID()));
			    }else{
			    	dataBaseUser.setStdProductPromPrice("");
			    }
			   System.out.println("促销价格:"+dataBaseUser.getStdProductPromPrice());
			    
			 /*    
			  *   综合商品价格和商品促销价格，更新价格
			  * */
			    if(dataBaseUser.getProductPrice()!=null&&!dataBaseUser.getProductPrice().equals("")){
			    	String stdPrice = dataBaseUser.getStdProductPrice();
			    	if(dataBaseUser.getStdProductPromPrice()!=null&&!dataBaseUser.getStdProductPromPrice().equals("")){
			    		stdPrice = dataBaseUser.getStdProductPromPrice();
			    	}
			    	dataBaseUser.setStdPrice(stdPrice);
			    }else{
			    	dataBaseUser.setStdPrice("");
			    }
			       
			    /*处理storeURL*/
				System.out.println("******分割线******");
				System.out.println("更新storeURL:"+dataBaseUser.getStoreURL());
			    if(dataBaseUser.getStoreURL()!=null&&!dataBaseUser.getStoreURL().equals("")){
			    	String URL = dataBaseUser.getStoreURL();
			    	if(URL.startsWith("//")){
			    		URL="http:"+URL;
			    	}
			    	dataBaseUser.setStoreURL(URL);
			    }
			    System.out.println("storeURL:"+dataBaseUser.getStoreURL());

			    
			    
			    
			    System.out.println("******分割线******");
				System.out.println("更新时间"+extractTime);
				//注意：没有具体到小时
				String timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(extractTime);
				System.out.println("extractTime:"+timeFormat);
				int year=extractTime.getYear()+1900;
				int month=extractTime.getMonth()+1;//月份少一个月，一月对应0，十二月对应11，所以加了1
				int day = 0;
				String first = timeFormat.split("-")[2];
				first = first.substring(0, 2);
				day =Integer.parseInt(first) ;
				String analyzeTime = null;
				if(day<20){//超过采集月份
					extractTime.setDate(1);
					extractTime.setHours(0);
					extractTime.setMinutes(0);
					extractTime.setSeconds(0);
					extractTime=new Date(extractTime.getTime()-1000);
					analyzeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(extractTime);
					year=extractTime.getYear()+1900;
				    month=extractTime.getMonth()+1; 
				    String second = analyzeTime.split("-")[2];
				    second = second.substring(0, 2);
					day =Integer.parseInt(second) ;
				}else{
					analyzeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(extractTime);
				}
	
				
			/*	String extraTime = extractTime.toString();
				String extractTimeString = extraTime.substring(0, extraTime.indexOf("."));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    extractTime = sdf.parse(extractTimeString);//
				//extractTime=sdf.format(extractTime);
			    System.out.println(extractTime);
				
				DateTimeFormatter formatter2 = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");  
				LocalDate localDate2 =null;
				//String year="20"+localDate.getYearOfCentury();
				//int month = localDate.getMonthOfYear();
				int 
				System.out.println("1");
				if(YEAR.equals(year)&&day<10){
					System.out.println("2");
					SimpleDateFormat bartDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); 
					System.out.println("3");
					
					extractTime.setDate(1);
					extractTime.setHours(0);
					extractTime.setMinutes(0);
					extractTime.setSeconds(0);
					System.out.println("4");
					
					System.out.println("5");
					extractTime=new Date(extractTime.getTime()-1000); //
					System.out.println("6");
					
					month = month -1;
					//day还需要判断
					SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
				    String lastTime=YEAR+"-"+month+"-"+"30"+" "+"23:59:59";
				    System.out.println(lastTime);
					localDate2 = formatter2.parseLocalDate(bartDateFormat.format(extractTime).toString()); 
				 
				}
				else{
					 localDate2 = formatter2.parseLocalDate(extractTime.toString());
				}
				*/
				
				/*System.out.println("yearOfCentury: " + "20"+localDate.getYearOfCentury()); 
				System.out.println("monthOfYear: " + localDate.getMonthOfYear()); 
				System.out.println("dayOfMonth: " + localDate.getDayOfMonth());*/
				
				
			   /*规范化类型*/
			   System.out.println("******分割线******");
			   JudgeMent judgeMent = new JudgeMent();
			   System.out.println("stdProvinceCode");
			   int stdProvinceCode = judgeMent.judgeInt(dataBaseUser.getStdProvinceCode(),"stdProvinceCode",dataBaseUser.getDataID());
			   System.out.println("stdCityCode");
			   int stdCityCode = judgeMent.judgeInt(dataBaseUser.getStdCityCode(),"stdCityCode",dataBaseUser.getDataID());
			   System.out.println("stdProductPrice");
			   float stdProductPrice = judgeMent.judgeFloat(dataBaseUser.getStdProductPrice(),"stdProductPrice",dataBaseUser.getDataID());
			   System.out.println("stdProductPromPrice");
			   float stdProductPromPrice = judgeMent.judgeFloat(dataBaseUser.getStdProductPromPrice(),"stdProductPromPrice",dataBaseUser.getDataID());
			   System.out.println("stdPrice");
			   float stdPrice =judgeMent.judgeFloat(dataBaseUser.getStdPrice(),"stdPrice",dataBaseUser.getDataID());
			   System.out.println("stdDeliveryProvinceCode");
			   int stdDeliveryProvinceCode = judgeMent.judgeInt(dataBaseUser.getStdDeliveryProvinceCode(),"stdDeliveryProvinceCode",dataBaseUser.getDataID());
			   System.out.println("stdDeliveryCityCode");
			   int stdDeliveryCityCode = judgeMent.judgeInt(dataBaseUser.getStdDeliveryCityCode(),"stdDeliveryCityCode",dataBaseUser.getDataID());
			   System.out.println("stdStoreLocationProvinceCode");
			   int stdStoreLocationProvinceCode = judgeMent.judgeInt(dataBaseUser.getStdStoreLocationProvinceCode(),"stdStoreLocationProvinceCode",dataBaseUser.getDataID());
			   System.out.println("stdStoreLocationCityCode");
			   int stdStoreLocationCityCode = judgeMent.judgeInt(dataBaseUser.getStdStoreLocationCityCode(),"stdStoreLocationCityCode",dataBaseUser.getDataID());
			   System.out.println("StdWeightValue");
			   float StdWeightValue = judgeMent.judgeFloat(dataBaseUser.getStdWeightValue(),"StdWeightValue",dataBaseUser.getDataID());
			   System.out.println("productLocationCode");
			   int productLocationCode = judgeMent.judgeInt(String.valueOf(judgeMent.getLocationCode(stdProvinceCode, stdCityCode)),"productLocationCode",dataBaseUser.getDataID());
			   System.out.println("storeLocationCode");
			   int storeLocationCode = judgeMent.judgeInt(String.valueOf(judgeMent.getLocationCode(stdStoreLocationProvinceCode, stdStoreLocationCityCode)),"storeLocationCode",dataBaseUser.getDataID());
			   
			   
			   /*生成sql数据*/
			   System.out.println("******分割线******");
			   System.out.println("开始更新啦！");
			   String updateStdProvince="update "+table+"  set "+judgeMent.updateNull("std_province", dataBaseUser.getStdProvince())+
		         ","+judgeMent.updateNull("std_provinceCode", stdProvinceCode)+
		         ","+judgeMent.updateNull("std_city",dataBaseUser.getStdCity())+
			     ","+judgeMent.updateNull("std_cityCode",stdCityCode)+
			     ","+judgeMent.updateNull("std_stdProductPrice",stdProductPrice)+
			     ","+judgeMent.updateNull("std_stdProductPromPrice",stdProductPromPrice)+
			     ","+judgeMent.updateNull("std_stdPrice",stdPrice)+
			     ","+judgeMent.updateNull("std_deliveryProvince",dataBaseUser.getStdDeliveryProvince())+
			     ","+judgeMent.updateNull("std_deliveryProvinceCode",stdDeliveryProvinceCode)+
			     ","+judgeMent.updateNull("std_deliveryCity",dataBaseUser.getStdDeliveryCity())+
			     ","+judgeMent.updateNull("std_deliveryCityCode",stdDeliveryCityCode)+
			     ","+judgeMent.updateNull("std_storeLocationProvince",dataBaseUser.getStdStoreLocationProvince())+
			     ","+judgeMent.updateNull("std_storeLocationProvinceCode",stdStoreLocationProvinceCode) +
			     ","+judgeMent.updateNull("std_storeLocationCity",dataBaseUser.getStdStoreLocationCity()) +
			     ","+judgeMent.updateNull("std_storeLocationCityCode",stdStoreLocationCityCode)+
			     ","+judgeMent.updateNull("std_stdWeightValue",StdWeightValue)+
			     ","+judgeMent.updateNull("std_storeURL",dataBaseUser.getStoreURL())+
			     ","+judgeMent.updateNull("productLocationCode",productLocationCode)+
			     ",analyzeTime='"+analyzeTime+
			     "',"+judgeMent.updateNull("storeLocationCode",storeLocationCode)+
			     ",year="+year+
			     ",monthOfYear="+month+
			     ",dayOfMonth="+day+
			     ",isOperation=1"+
			     " where dataID="+ dataBaseUser.getDataID();
			   System.out.println(updateStdProvince);
			    stmt1.executeUpdate(updateStdProvince); 
			    
		    }
		    System.out.println("总共执行了："+count+"行");
		} catch (Exception e) {
           
			System.out.println(e.toString());
		}finally{
			JdbcUtil.closeAll(conn, stmt, rs);
		}
	
	}
	
}
