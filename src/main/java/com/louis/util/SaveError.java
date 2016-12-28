package com.louis.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月10日 下午11:12:55 
 * 类说明 :实现保存错误信息
 */
public class SaveError {

	public static void errorAsText(String error, String filePath) throws IOException {
		//得到long类型当前时间
		long l = System.currentTimeMillis();
		//new日期对象
		Date date = new Date(l);
		//转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileOutputStream fileOutputStream = new FileOutputStream(filePath, true);
	    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
		outputStreamWriter.write(error+"-"+dateFormat.format(date)+"\n");
		outputStreamWriter.flush();
		outputStreamWriter.close();
		
	}
}
