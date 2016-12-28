package com.louis.IDataPreProcess;

import java.util.HashMap;
import java.util.List;

/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月11日 上午11:58:27 
 * 类说明 
 */
public interface IDataPreProcess {
	//得到标准省份
	public HashMap<String,String> getStdProvince(String orignalProvince,String space,String count,String ID,List<HashMap<String,String>> regionCodeName );
	//得到标准城市
	public HashMap<String, String> getStdCity(String orignalCity,String space,String count,String wholeProvinceCode,String ID,List<HashMap<String,String>> regionCodeName);
	//得到标准形式的价格
	public String getStdProductPrice(String orignalProductPrice,String space,String count,String ID);
	//得到标准形式的促销价格
	public String getStdProductPromPrice(String orignalProductPromPrice,String space,String ID);
	//得到标准形式的发货省份
	public String getStdDeliveryProvince(String orignalDelivery);
	//得到标准形式的发货城市
	public String getStdDeliveryCity(String orignalDelivery);
	//得到标准形式的店铺省份
	public String getStdStoreLocationProvince(String orignalLocation);
	//得到标准形式的店铺城市
	public String getStdStoreLocationCity(String orignalCity);
	//得到标准形式的重量值
    public String getStdWeightValue(String orignalWeight,String space,String count,String ID);
    //得到城市的编码
    public String getCityCode(String stdCity);
    //得到发货地城市的编码
    public String getDeliveryCityCode(String stdDeliveryCity);
    //得到店铺城市的编码
    public String getStoreLocationCityCode(String stdStoreLocationCity);
    //得到标准形式的发货省份和城市
    public List<HashMap<String,String>> getStdDelivery(String orignalDelivery,String space,String count,String ID,List<HashMap<String,String>> regionCodeName);
}
