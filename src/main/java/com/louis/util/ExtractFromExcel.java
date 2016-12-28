package com.louis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月11日 下午4:30:02 
 * 类说明 :从Excel表中提取出地区名称及编号
 */
public class ExtractFromExcel {

    /*
     * 读excel,获得地名和编号
     * */
	@Test
    public List<HashMap<String,String>> getRegionFromExcel() throws IOException, Exception{
    	List<String> regionCode = new ArrayList<String>();
    	List<String> regionName = new ArrayList<String>();
        List<HashMap<String,String>> regionCodeName = new ArrayList<HashMap<String, String>>();
        String curPath = ExtractFromExcel.class.getClassLoader().getResource("").toURI().getPath();
      /*  System.out.println(curPath);*/
        String resourcePath = curPath+"region.xls";
        File excelFile = new File(resourcePath);
        FileInputStream fileInputStream  = new FileInputStream(excelFile);
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fileInputStream);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        
        int rowStart = hssfSheet.getFirstRowNum();
        int rowEnd = hssfSheet.getLastRowNum();
        
        Row row = null;
        Cell cell1 = null;
        Cell cell2 = null;
        for (int i = rowStart; i <=rowEnd; i++) {
			row = hssfSheet.getRow(i);
			cell1 = row.getCell(1);
			cell2 = row.getCell(2);
			String cellCode = cell1.getStringCellValue().trim();
			String cellRegion = cell2.getStringCellValue().trim();
			HashMap<String, String> hashmap = new HashMap<>();
			hashmap.put(cellCode, cellRegion);
			regionCodeName.add(hashmap);
		}
        
        //Entry遍历list<map>
        /* for (int i = 0; i < regionCodeName.size(); i++) {
        	HashMap<String, String> map = regionCodeName.get(i);
        	Set<Entry<String, String>> sets = map.entrySet();
        	for(Entry<String, String> entry : sets){
        		System.out.print(entry.getKey()+",");
        		System.out.println(entry.getValue());
        	}
        	
		}*/
        return regionCodeName;
        
    }
   
}
