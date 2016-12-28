package com.louis.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.helpers.FileWatchdog;

import com.louis.DataPreProcessImpl.DataPreProcessImpl;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月12日 上午11:43:13 
 * 类说明 
 */
public class test {

	public static void main(String[] args) throws IOException, URISyntaxException {
		int count = 0;
		int duan = 0;
		for (int i = 0; i < 10; i++) {
			duan = count++;
		}
		System.out.println(duan);
		
		/*		String str = "13</b>.50";
		String str1 = str.substring(0,str.indexOf("<"));
		String str2 = str.substring(str.indexOf("."),str.length());
		System.out.println(str1);
		System.out.println(str2);*/
/*SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date extractTime ="2016-10-30 23:59:59";
		Date analyzeTime=null;
		extractTime.setDate(1);
		extractTime.setHours(0);
		extractTime.setMinutes(0);
		extractTime.setSeconds(0);
		//System.out.println(extractTime);
		analyzeTime=new Date(extractTime.getTime()-1000);
		//System.out.println(bartDateFormat.format(analyzeTime));
		return analyzeTime;
		String str = "2016-10-30 23:59:59";
		try {
			Date extractTime = new SimpleDateFormat("YYYY-MM-dd").parse(str);
			System.out.println(extractTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String weight = "123-3445";
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	    Matcher m = p.matcher(weight);
		if(weight.matches(".*[a-zA-z].*")||m.find()||weight.contains("-")){
			System.out.println("正确");
		}*/
	/*	Pattern patternCutData = Pattern.compile("\\d+");
		Matcher matcher = patternCutData.matcher(weight);
		while (matcher.find()) {
			weight = matcher.group(0);
			break;
			
		}
		System.out.println(weight);*/
	/*	String weight = "15x10x13";
		System.out.println(DataPreProcessImpl.countX(weight));*/
	
		
/*		String s1 = "300克X20";
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(s1);
		while (matcher.find()) {
			System.err.println(matcher.group(0));
			break;
		}*/
		
	/*	DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss"); 
		LocalDate localDate = formatter.parseLocalDate("2016-10-24 23:55:48"); 
		System.out.println("yearOfCentury: " + "20"+localDate.getYearOfCentury()); 
		System.out.println("monthOfYear: " + localDate.getMonthOfYear()); 
		System.out.println("dayOfMonth: " + localDate.getDayOfMonth());*/
		/*String a="love23next234csdn3423javaeye";
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(a);   
		System.out.println( m.replaceAll("").trim());*/

	/*	//得到long类型当前时间
		long l = System.currentTimeMillis();
		//new日期对象
		Date date = new Date(l);
		//转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(dateFormat.format(date));*/
		/*String str= "湖南长沙";
		String  duan = "湖南";
		if(str.length()==4){
			System.out.println("yes");
		}
		str=str.substring(2, str.length());
		System.out.println(duan.length());
		System.out.println(str);*/
		
		/*String orignalWeight = "1100g1.1kg(110g*10袋)";
		String stdWightValue = "";
		String orignalWeightValue = "";
		String regEx = "(\\d+(.*\\d)*)";
		Pattern pattern = Pattern.compile(regEx);
		Matcher m = pattern.matcher(orignalWeight);
		while (m.find()) {
			orignalWeightValue  = m.group();
		}
		System.out.println(orignalWeightValue);*/
		
		
		
		
		
	/*	String str="1000g5kg";
		str=str.substring(0, str.indexOf("g"));
		System.out.println(str);*/
	/*	String str="";
		System.out.println(Integer.parseInt(str)+1);*/
		
		
/*String str = "金额：88,687,594元";
		Pattern p = Pattern.compile("\\d+(,\\d{3})*");
		Matcher m = p.matcher(str);
		if(m.find()){
			System.out.println(m.group());
		}*/

	/*	String orignalWeight = "90.99千克";
		String regEx = "\\d+(.*\\d)*";
		String mi =null;
		Pattern pattern = Pattern.compile(regEx);
		Matcher m = pattern.matcher(orignalWeight);
		while (m.find()) {
			mi = m.group();
			System.out.println(mi);
		}
		String stdWightValue = String.valueOf((int)(Float.parseFloat(mi)*1000));
		System.out.println(stdWightValue);*/
/*		String mi = m.group(1);
		System.out.println("mi"+mi);*/
		/*String orignalWeightValue = m.replaceAll("").trim();
		System.out.println("输出值："+orignalWeightValue);*/
/*		String filePath =  "output/orignalProvince.txt";
		System.out.println(filePath);
		String[] str = {"年号","哈哈","jajjs"};
		for (int i = 0; i < str.length; i++) {
			FileOutputStream fileOutputStream = new FileOutputStream(filePath, true);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
			outputStreamWriter.write(str[i]+"\n");
			outputStreamWriter.flush();
		}
		*/
	/*	String filePath;
		filePath = test.class.getClassLoader().getResource("").toURI().getPath();
		filePath = filePath + "output/orignalProvince.txt";
		System.out.println(filePath);
		File file = new File(filePath);
		file.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write("niaho ");
		bw.close();*/
	/*	for(int i=1;i<6;i++){
			
			System.out.println("保存成功");
		
			
			bw.write("错误"+i);
			bw.newLine();
			System.out.println("再写");
			bw.close();
		
		}*/
	
	}
}
