package com.louis.DataPreProcessImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.ptg.Ptg;

import com.louis.IDataPreProcess.IDataPreProcess;
import com.louis.util.ExtractFromExcel;
import com.louis.util.SaveError;

import java.sql.PreparedStatement;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月11日 下午3:58:32 
 * 类说明 
 */
public class DataPreProcessImpl implements IDataPreProcess {
	public static Logger logger = Logger.getLogger("com.foo");
	
	/*
	 * 获取标准省份
	 * */
	public HashMap<String,String> getStdProvince(String orignalProvince,String space,String count,String ID,List<HashMap<String,String>> regionCodeName,int productInnerID,String extractTimeStr,PreparedStatement pstmt,Connection DBCPconn) {
		logger.setLevel(Level.DEBUG);
		//保存返回的省份和编号
		HashMap<String,String> regionCode = new HashMap<>();
		//返回的省份
		String province = "";
		//返回的编号
		String code = "";
		//查询到的省份，用来计算有几个。
		List<String> stdProvince = new ArrayList<>();
		List<String> stdProvinceCode = new ArrayList<>();
		try {
			for (int i = 0; i < regionCodeName.size(); i++) {
				HashMap<String,String> map = regionCodeName.get(i);
				Set<Entry<String, String>> sets = map.entrySet();
				for (Entry<String, String> entry : sets) {
					String regionName = entry.getValue();
					regionName = regionName.trim();
					char[] regionNameArray = regionName.toCharArray();
					//必须从regionName的第一个字开始匹配成功并且至少匹配成功两个才算成功
					//注意表格中有空格
					if(regionNameArray.length<2){
						continue;
					}
					char RegionName1 = regionNameArray[0];
					char RegionName2 = regionNameArray[1];
					String strRegion = String.valueOf(RegionName1)+String.valueOf(RegionName2);
					//originalProvince可能带有省份加进来
					//判断出现regionName1出现的位置和regionName2出现的位置是否紧挨着
					if(orignalProvince.contains(strRegion)){
						/*stdProvinceCode.add(entry.getKey());*/
						String  Code = entry.getKey();
					    if(Code.contains("0000")){
					    	stdProvince.add(regionName);
					    	stdProvinceCode.add(Code);
					    	/*System.out.println(regionName);
							System.out.println("找到省份");*/
						}
					}
				}
				
			}
			/*
			 * 判断是否会出现太和县和太和区不同的情况/阿富汗的情况
			 * */
			if(stdProvince.size()>=2||stdProvince.size()==0){
				//判断，如果有输入参数，则
				String filePath = "output/"+space+".txt";
				//使用log4j
				logger.warn(orignalProvince+"    第"+count+"行:"+space+"    "+ID);
				//使用输出错误到文件中
				SaveError.errorAsText(orignalProvince, filePath);
				regionCode.put("","");
				pstmt.setInt(1, Integer.parseInt(ID));
				pstmt.setInt(2,productInnerID);
				pstmt.setString(3, orignalProvince+"    第"+count+"行:"+space+"    "+ID);
				pstmt.setString(4, extractTimeStr);
				
				pstmt.execute();
				
				
			}else if(stdProvince.size()==1){
				province = stdProvince.get(0);
				code = stdProvinceCode.get(0);
				regionCode.put(code, province);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regionCode;
	}

	/*
	 * 获取标准城市
	 * */
	public HashMap<String, String> getStdCity(String orignalCity,String space,String count,String wholeProvinceCode,String ID,List<HashMap<String,String>> regionCodeName,int productInnerID,String extractTimeStr,PreparedStatement pstmt ) {
		logger.setLevel(Level.DEBUG);
		int isBreak = 0;
		//保存返回的城市和编号
		HashMap<String,String> cityAndCode = new HashMap<>();
		//返回的省份
		String city = "";
		//返回的编号
		String code = "";
		//查询到的省份，用来计算有几个。
		List<String> stdCity = new ArrayList<>();
		List<String> stdCityCode = new ArrayList<>();
		try {
			//遍历省市级划分表
			for (int i = 0; i < regionCodeName.size(); i++) {
				HashMap<String,String> map = regionCodeName.get(i);
				Set<Entry<String, String>> sets = map.entrySet();
				for (Entry<String, String> entry : sets) {
					String regionName = entry.getValue();
					regionName = regionName.trim();
					char[] regionNameArray = regionName.toCharArray();
					//必须从regionName的第一个字开始匹配成功并且至少匹配成功两个才算成功
					//注意表格中有空格
					if(regionNameArray.length<2){
						continue;
					}
					char RegionName1 = regionNameArray[0];
					char RegionName2 = regionNameArray[1];
					String strRegion = String.valueOf(RegionName1)+String.valueOf(RegionName2);
					//城市的编码应该结合前面的省份去考虑，编码值范围这里设定的是大于省份编码且小于省份编码+100000
					if(orignalCity.contains(strRegion)){
						String  Code = entry.getKey();
						
						if (Code.contains("00")) {
							stdCity.add(regionName);
							stdCityCode.add(Code);
							System.out.println("标准城市编码："+Code);
							isBreak = 1;
							break;
						}else if(!Code.contains("00")){
							if((!"".equals(Code)&&Code!=null)&&!"".equals(wholeProvinceCode)&&wholeProvinceCode!=null){
								if(Integer.parseInt(Code)>Integer.parseInt(wholeProvinceCode)&&Integer.parseInt(Code)<Integer.parseInt(wholeProvinceCode)+1000000){
									stdCity.add(regionName);
							    	stdCityCode.add(Code);
							    	System.out.println("标准城市编码："+Code);
							    	isBreak++;
								}
							}
						}

					}
					
				}
				if(isBreak==1){
					break;
				}
			}
			/*
			 * 判断是否会阿富汗的情况
			 * */
			if(stdCity.size()==0||stdCity.size()>=2){
				String filePath = "output/"+space+".txt";
				//使用log4j
				logger.warn(orignalCity+"-第"+count+"行:"+space+"-"+ID+"-标准城市大小："+stdCity.size());
				//使用输出错误到文件中
				SaveError.errorAsText(orignalCity, filePath);
				cityAndCode.put("","");
				pstmt.setInt(1, Integer.parseInt(ID));
				pstmt.setInt(2,productInnerID);
				pstmt.setString(3, orignalCity+"-第"+count+"行:"+space+"-"+ID+"-标准城市大小："+stdCity.size());
				pstmt.setString(4, extractTimeStr);
				
				pstmt.execute();
				
			}else if(stdCity.size()==1){
				city = stdCity.get(0);
				code = stdCityCode.get(0);
				cityAndCode.put(code, city);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityAndCode;
	}
    /*
     * 获取发货地的标准省份和标准城市，注意：其中一个会出现null的情况
     * */
	public List<HashMap<String,String>> getStdDelivery(String orignalDelivery,String space,String count,String ID,List<HashMap<String,String>> regionCodeName,int productInnerID,String extractTimeStr,PreparedStatement pstmt){
		logger.setLevel(Level.DEBUG);
		//保存返回的<省份，省份编号>,<城市，城市编号>
		List<HashMap<String, String>> list = new ArrayList<>();
		//保存返回的城市和编号
		HashMap<String,String> cityAndCode = new HashMap<>();
		HashMap<String,String> provinceAndCode = new HashMap<>();
		// 返回的省份
		String province = "";
		// 返回的编号
		String pcode = "";
		// 返回的省份
		String city = "";
		//返回的编号
		String code = "";
		int isBreak = 0;
		String wholeDeProvinceCode="";
		//查询到的省份，用来计算有几个。
		List<String> stdProvince = new ArrayList<>();
		List<String> stdProvinceCode = new ArrayList<>();
		List<String> stdCity = new ArrayList<>();
		List<String> stdCityCode = new ArrayList<>();
		try {
			//遍历省市级划分表
			for (int i = 0; i < regionCodeName.size(); i++) {
				HashMap<String,String> map = regionCodeName.get(i);
				Set<Entry<String, String>> sets = map.entrySet();
				for (Entry<String, String> entry : sets) {
					String regionName = entry.getValue();
					//excel表中的地名和编码
					regionName = regionName.trim();
					String  Code = entry.getKey();
					char[] regionNameArray = regionName.toCharArray();
					//必须从regionName的第一个字开始匹配成功并且至少匹配成功两个才算成功
					//注意表格中有空格
					if(regionNameArray.length<2){
						continue;
					}
					char RegionName1 = regionNameArray[0];
					char RegionName2 = regionNameArray[1];
					//excel表中地名的前两个字
					String strRegion = String.valueOf(RegionName1)+String.valueOf(RegionName2);
					//originalCity可能带有省份加进来
					if(orignalDelivery.contains(strRegion)){
						if (Code.contains("0000")) {
							stdProvince.add(regionName);
							stdProvinceCode.add(Code);
							wholeDeProvinceCode = Code;
						} else if(!Code.contains("0000")&&!"".equals(Code)&&Code!=null&&!"".equals(wholeDeProvinceCode)&&wholeDeProvinceCode!=null){
							if(Integer.parseInt(Code)>Integer.parseInt(wholeDeProvinceCode)&&Integer.parseInt(Code)<Integer.parseInt(wholeDeProvinceCode)+10000) {
								if(Code.contains("00")){
									stdCity.add(regionName);
									stdCityCode.add(Code);
									isBreak= 1;
									break;
									
								}else{
									stdCity.add(regionName);
									stdCityCode.add(Code);
									//20161024  下午加的代码,还没试
									isBreak=1;
									break;
								}
							}
						}else{
							if(Code.contains("00")&&!Code.contains("0000")){
								stdCity.add(regionName);
								stdCityCode.add(Code);
								isBreak= 1;
								break;
							}else {
								stdCity.add(regionName);
								stdCityCode.add(Code);
								isBreak= 1;
								break;
							}
						}
					}
				}
				if(isBreak ==1){
					break;
				}
			}
			/*System.out.println("发货地址或者店铺地址"+stdCity.size());*/
			/*
			 * 省份和城市：如果两个有一个长度大于2说明有问题（太和）；如果两个的长度都等于0，说明有问题（阿富汗）；如果其中有一个或者两者都等于一说明是正确的
			 * */
			if(stdCity.size()>=2||stdProvince.size()>=2){
				String filePath = "output/"+space+".txt";
				//使用log4j
				logger.warn(orignalDelivery+"-第"+count+"行:"+space+"-"+ID+"-发货地址或者店铺地址大小:"+stdCity.size());
				provinceAndCode.put("", "");
				cityAndCode.put("", "");
				//使用输出错误到文件中
				SaveError.errorAsText(orignalDelivery, filePath);	
				pstmt.setInt(1, Integer.parseInt(ID));
				pstmt.setInt(2,productInnerID);
				pstmt.setString(3, orignalDelivery+"-第"+count+"行:"+space+"-"+ID+"-发货地址或者店铺地址大小:"+stdCity.size());
				pstmt.setString(4, extractTimeStr);
			
				pstmt.execute();
			}else if(stdCity.size()==1&&stdProvince.size()==0){
				city = stdCity.get(0);
				code = stdCityCode.get(0);
				province = "";
				pcode = "";
				provinceAndCode.put(pcode, province);
				cityAndCode.put(code, city);
			}else if(stdProvince.size()==1&&stdCity.size()==0){
				province = stdProvince.get(0);
				pcode = stdProvinceCode.get(0);
				city = "";
				code = "";
				provinceAndCode.put(pcode, province);
				cityAndCode.put(code, city);
			}else if(stdProvince.size()==1&&stdCity.size()==1){
				province = stdProvince.get(0);
				pcode = stdProvinceCode.get(0);
				city = stdCity.get(0);
				code = stdCityCode.get(0);
				provinceAndCode.put(pcode, province);
				cityAndCode.put(code, city);
			}else if(stdCity.size()==0&&stdProvince.size()==0){
				provinceAndCode.put("", "");
				cityAndCode.put("", "");
				String filePath = "output/"+space+".txt";
				//使用log4j
				logger.warn(orignalDelivery+"-第"+count+"行:"+space+"-"+ID+"-发货地址或者店铺地址大小:"+stdCity.size());
				//使用输出错误到文件中
				SaveError.errorAsText(orignalDelivery, filePath);	
				pstmt.setInt(1, Integer.parseInt(ID));
				pstmt.setInt(2,productInnerID);
				pstmt.setString(3, orignalDelivery+"-第"+count+"行:"+space+"-"+ID+"-发货地址或者店铺地址大小:"+stdCity.size());
				pstmt.setString(4, extractTimeStr);
				
				pstmt.execute();
			}
			list.add(provinceAndCode);
			list.add(cityAndCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public String getStdProductPrice(String orignalProductPrice,String space,String count,String ID,int productInnerID,String extractTimeStr,PreparedStatement pstmt) {
		logger.setLevel(Level.DEBUG);
		String[] strError = {"-",",","，"};
		String stdProductPrice = "";
		try {
			if(!(orignalProductPrice.contains(strError[0])||orignalProductPrice.contains(strError[1])||orignalProductPrice.contains(strError[2]))){
				stdProductPrice = orignalProductPrice;
			}else if(orignalProductPrice.contains(strError[0])){
				String[] str = orignalProductPrice.split(strError[0]);
				float a = Float.parseFloat(str[0]);
				float b = Float.parseFloat(str[1]);
				float c = (a+b)/2;
				stdProductPrice = String.valueOf(c);
				return stdProductPrice;
			}else if(orignalProductPrice.contains(strError[1])){
				String[] str = orignalProductPrice.split(strError[1]);
				float a = Float.parseFloat(str[0]);
				float b = Float.parseFloat(str[1]);
				float c = (a+b)/2;
				stdProductPrice = String.valueOf(c);
				return stdProductPrice;
			}else if(orignalProductPrice.contains(strError[2])){
				String[] str = orignalProductPrice.split(strError[2]);
				float a = Float.parseFloat(str[0]);
				float b = Float.parseFloat(str[1]);
				float c = (a+b)/2;
				stdProductPrice = String.valueOf(c);
				return stdProductPrice;
			}else{
				//出现数组越界情况时
				//判断，如果有输入参数，则
				String filePath = "output/"+space+".txt";
				//使用log4j
				logger.warn(orignalProductPrice+"-第"+count+"行:"+space+"-"+ID);
				//使用输出错误到文件中
				try {
					SaveError.errorAsText(orignalProductPrice, filePath);
					pstmt.setInt(1, Integer.parseInt(ID));
					pstmt.setInt(2,productInnerID);
					pstmt.setString(3, orignalProductPrice+"-第"+count+"行:"+space+"-"+ID);
					pstmt.setString(4, extractTimeStr);
					
					pstmt.execute();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.warn(e+"-"+"ID:"+ID+"-"+space);
		}
		
		return stdProductPrice;
	}

	@Override
	public String getStdProductPromPrice(String orignalProductPromPrice,String space,String ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStdDeliveryProvince(String orignalDelivery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStdDeliveryCity(String orignalDelivery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStdStoreLocationProvince(String orignalLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStdStoreLocationCity(String orignalCity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStdWeightValue(String orignalWeightValue,String space,String count,String ID,int productInnerID,String extractTimeStr,PreparedStatement pstmt) {	
		logger.setLevel(Level.DEBUG);
		//保存标准数据
		String stdWightValue = "";
		String orignalWeightValueValue = "";
		String orignalWeightValueValue1 = "";
		String[] strError = {"含","以","装"};
		String[] strKg = {"千克","kg","l","公斤","l","升","k","k"};
		String[] strJin = {"斤"};
		String[] strg = {"克","g","ml","g","毫升"};
		String regEx = "\\d+(.*\\d)*";//\\d+(.*\\d)*
		String regExNoNumber = "^(\\d+(.*\\d)*)$";
		//为了去掉多余的汉子，所以多执行几次
		Pattern pattern = Pattern.compile(regEx);
		Matcher m = pattern.matcher(orignalWeightValue);
		while (m.find()) {
			orignalWeightValueValue  = m.group();
		}
	
		
		//将所有数据都转化成小写
		orignalWeightValueValue = orignalWeightValueValue.toLowerCase();
		if(orignalWeightValueValue.contains("g")){
			Pattern pattern2 = Pattern.compile(regEx);
			Matcher m2 = pattern2.matcher(orignalWeightValueValue);
			while (m2.find()) {
				orignalWeightValueValue  = m2.group();
			}
			Pattern pattern3 = Pattern.compile(regEx);
			Matcher m3 = pattern3.matcher(orignalWeightValueValue);
			while (m3.find()) {
				orignalWeightValueValue  = m3.group();
			}
			orignalWeightValueValue=orignalWeightValueValue.substring(0, orignalWeightValueValue.indexOf("g"));
		}
		Pattern pattern1 = Pattern.compile(regExNoNumber);
		Matcher n = pattern1.matcher(orignalWeightValueValue);
		while (n.find()) {
			orignalWeightValueValue1  = n.group();
		}
		try {
			if(!"".equals(orignalWeightValueValue1)&&orignalWeightValueValue1!=null){
				//为了去掉多余的汉子，所以多执行几次
				Pattern pattern2 = Pattern.compile(regEx);
				Matcher m2 = pattern2.matcher(orignalWeightValueValue);
				while (m2.find()) {
					orignalWeightValueValue  = m2.group();
				}
				Pattern pattern3 = Pattern.compile(regEx);
				Matcher m3 = pattern3.matcher(orignalWeightValueValue);
				
				while (m3.find()) {
					orignalWeightValueValue  = m3.group();
				}
				stdWightValue=orignalWeightValueValue1;
			}else if(orignalWeightValueValue.contains(strError[0])||orignalWeightValueValue.contains(strError[1])||orignalWeightValueValue.contains(strError[2])){
				if(orignalWeightValueValue.contains(strg[0])){
					Pattern pattern2 = Pattern.compile(regEx);
					Matcher m2 = pattern2.matcher(orignalWeightValueValue);
					while (m2.find()) {
						orignalWeightValueValue  = m2.group();
					}
					Pattern pattern3 = Pattern.compile(regEx);
					Matcher m3 = pattern3.matcher(orignalWeightValueValue);
					while (m3.find()) {
						orignalWeightValueValue  = m3.group();
					}
					stdWightValue=orignalWeightValueValue.substring(0, orignalWeightValueValue.indexOf("克"));
				}else if(orignalWeightValueValue.contains(strg[1])){
					Pattern pattern2 = Pattern.compile(regEx);
					Matcher m2 = pattern2.matcher(orignalWeightValueValue);
					while (m2.find()) {
						orignalWeightValueValue  = m2.group();
					}
					Pattern pattern3 = Pattern.compile(regEx);
					Matcher m3 = pattern3.matcher(orignalWeightValueValue);
					while (m3.find()) {
						orignalWeightValueValue  = m3.group();
					}
					stdWightValue=orignalWeightValueValue.substring(0, orignalWeightValueValue.indexOf("g"));
				}
				
			}else if(orignalWeightValueValue.contains(strJin[0])){
				Pattern pattern2 = Pattern.compile(regEx);
				Matcher m2 = pattern2.matcher(orignalWeightValueValue);
				while (m2.find()) {
					orignalWeightValueValue  = m2.group();
				}
				Pattern pattern3 = Pattern.compile(regEx);
				Matcher m3 = pattern3.matcher(orignalWeightValueValue);
				while (m3.find()) {
					orignalWeightValueValue  = m3.group();
				}
				stdWightValue = String.valueOf(Float.parseFloat(orignalWeightValueValue)*500);
			}else if(orignalWeightValueValue.contains(strg[0])||orignalWeightValueValue.contains(strg[1])||orignalWeightValueValue.contains(strg[2])||orignalWeightValueValue.contains(strg[3])||orignalWeightValueValue.contains(strg[4])){
				Pattern pattern2 = Pattern.compile(regEx);
				Matcher m2 = pattern2.matcher(orignalWeightValueValue);
				while (m2.find()) {
					orignalWeightValueValue  = m2.group();
				}
				Pattern pattern3 = Pattern.compile(regEx);
				Matcher m3 = pattern3.matcher(orignalWeightValueValue);
				while (m3.find()) {
					orignalWeightValueValue  = m3.group();
				}
				stdWightValue = orignalWeightValueValue;
				return stdWightValue;
			}else if(orignalWeightValueValue.contains(strKg[0])||orignalWeightValueValue.contains(strKg[1])||orignalWeightValueValue.contains(strKg[2])||orignalWeightValueValue.contains(strKg[3])||orignalWeightValueValue.contains(strKg[4])||orignalWeightValueValue.contains(strKg[5])||orignalWeightValueValue.contains(strKg[6])||orignalWeightValueValue.contains(strKg[7])){
				Pattern pattern2 = Pattern.compile(regEx);
				Matcher m2 = pattern2.matcher(orignalWeightValueValue);
				while (m2.find()) {
					orignalWeightValueValue  = m2.group();
				}
				Pattern pattern3 = Pattern.compile(regEx);
				Matcher m3 = pattern3.matcher(orignalWeightValueValue);
				while (m3.find()) {
					orignalWeightValueValue  = m3.group();
				}
				stdWightValue = String.valueOf((int)(Float.parseFloat(orignalWeightValueValue)*1000));
				return stdWightValue;
			}else {
				String filePath = "output/"+".txt";
				//使用log4j
				logger.warn(orignalWeightValue+"-第"+count+"行:"+space+"-"+ID);
				//使用输出错误到文件中
				pstmt.setInt(1, Integer.parseInt(ID));
				pstmt.setInt(2,productInnerID);
				pstmt.setString(3, orignalWeightValue+"-第"+count+"行:"+space+"-"+ID);
				pstmt.setString(4, extractTimeStr);
	
				pstmt.execute();
				try {
					SaveError.errorAsText(orignalWeightValue, filePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
		} catch (Exception e) {
			logger.warn(e+"-"+"ID:"+ID+"-"+space);
		}
		
		return stdWightValue;
	}
	
	/*工具函数：计算str有多少个X*/
	public static int countX(String str){
		int countx = 0 ;
		for(int i=0;i<str.length();i++){
			if("x".equals(""+str.toLowerCase().charAt(i))){
				countx++;
			}
		}
		
		return countx;
	}
	public static int countXX(String str){
		int countxx = 0 ;
		for(int i=0;i<str.length();i++){
			if("*".equals(""+str.toLowerCase().charAt(i))){
				countxx++;
			}
		}
		
		return countxx;
	}
	

	@Override
	public String getCityCode(String stdCity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeliveryCityCode(String stdDeliveryCity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStoreLocationCityCode(String stdStoreLocationCity) {
		// TODO Auto-generated method stub
		return null;
	}

}
