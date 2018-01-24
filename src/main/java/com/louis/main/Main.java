package com.louis.main;

import com.louis.operation.Process;
import com.louis.util.GetTotal;
import com.louis.util.GetUtils;



/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月10日 下午9:44:26 
 * 类说明 :主函数
 */
public class Main {
	//新表名
	private static String newTableName = GetUtils.getNewTableName();
	
	
	
	//数据库总数
	private static int totalCount= 0;
	//每次循环个数
	private static int eachCount=1000;
	
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		System.out.println("主函数");
		Process process = new Process();
		try {	
			//1、创建新表
			if(!GetTotal.isExist(newTableName)) {
				System.out.println("创建新表中...");
				process.newTable();
				System.out.println("向错误表到数据...");
				//2、错误的数据导进错误表中
				process.errorTable();
			}else {
				System.out.println("新表已经存在...");
				System.out.println("错误信息也已经保存");
			}
			totalCount = GetTotal.getTotal(newTableName);
			//测试用
		/*	totalCount = 7;*/
			//3、获取省份
			process.getProvince();
			//4、开始执行
			//每次取出一千条， limit 1000*i,1000
			//总共需要totalCount/1000次循环
			int frequency = totalCount/eachCount;
			int remainder = totalCount%eachCount;
			//记录循环次数；
			int count=0;
			for (int i = 0; i <frequency; i++) {
				/*process.getValues(1000*i,1000);*/
				process.getValues(0,eachCount);
				System.out.println("第"+i+"次循环");
				count++;
			
			}
			if(remainder>0) {
				/*process.getValues(frequency*1000,remainder);*/
				process.getValues(0,remainder);
				
			}
			System.out.println("总共循环了"+count+"次");
			
	     	
		} catch (Exception e) {
			System.out.println("主函数异常退出");
		}
		long end = System.currentTimeMillis();
		System.out.println("程序运行时间："+(end-start)+"毫秒");
	}
}
