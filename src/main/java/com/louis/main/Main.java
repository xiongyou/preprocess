package com.louis.main;

import com.louis.operation.Process;



/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月10日 下午9:44:26 
 * 类说明 :主函数
 */
public class Main {
	//
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println("主函数");
		Process process = new Process();
		try {			
		    /*process.newTable();
			process.errorTable();*/
	     	process.getValues();
		} catch (Exception e) {
			// TODO: handle exception
		}
		long end = System.currentTimeMillis();
		System.out.println("程序运行时间："+(end-start)+"毫秒");
	}
}
