package com.louis.domain;
/** 
 * @author Michael2397 2692613726@qq.com: 
 * @version 创建时间：2016年8月11日 上午11:38:36 
 * 类说明 :数据库实体类
 */
public class DataBaseUser {
	//ID
	private String dataID ;
	//商品名称
	private String productName;
	//省份
	private String province ;
	//城市
	private String city ;
	//发货地址
	private String deliveryStartArea ;
	//地铺地址
	private String storeLocation ;
	//商品价格
	private String productPrice;
	//促销价格
	private String productPromPrice ;
	//商品重量
	private String weight ;
	//标准化的省份
	private String stdProvince;
	//标准化的省份编码
	private String stdProvinceCode;
	//标准化的城市
	private String stdCity;
	//标准化的城市编码
	private String stdCityCode;
	//标准化的发货省份
	private String stdDeliveryProvince;
	//标准化的发货省份编码
	private String stdDeliveryProvinceCode;
	//标准化的发货城市
	private String stdDeliveryCity;
	//标准化的发货城市编码
	private String stdDeliveryCityCode;
	//标准化的商品价格
	private String stdProductPrice;
	//标准化的促销价格
	private String stdProductPromPrice;
	//商品价格和促销价格统一后的价格
	private String stdPrice;
	//标准化的重量（数字）
	private String stdWeightValue;
	//标准化的店铺所在的省份
	private String stdStoreLocationProvince;
	//标准化的店铺所在的省份编码
	private String stdStoreLocationProvinceCode;
	//标准化的店铺所在的城市
	private String stdStoreLocationCity;
	//标准化的店铺所在的城市编码
	private String stdStoreLocationCityCode;
	//URL
	private String storeURL;
	
	public String getStdPrice() {
		return stdPrice;
	}

	public void setStdPrice(String stdPrice) {
		this.stdPrice = stdPrice;
	}

	public String getStdDeliveryProvinceCode() {
		return stdDeliveryProvinceCode;
	}

	public void setStdDeliveryProvinceCode(String stdDeliveryProvinceCode) {
		this.stdDeliveryProvinceCode = stdDeliveryProvinceCode;
	}

	public String getStdStoreLocationProvinceCode() {
		return stdStoreLocationProvinceCode;
	}

	public void setStdStoreLocationProvinceCode(String stdStoreLocationProvinceCode) {
		this.stdStoreLocationProvinceCode = stdStoreLocationProvinceCode;
	}

	public String getStdProvinceCode() {
		return stdProvinceCode;
	}

	public void setStdProvinceCode(String stdProvinceCode) {
		this.stdProvinceCode = stdProvinceCode;
	}


	public String getStdWeightValue() {
		return stdWeightValue;
	}

	public void setStdWeightValue(String stdWeightValue) {
		this.stdWeightValue = stdWeightValue;
	}

	public String getStdStoreLocationProvince() {
		return stdStoreLocationProvince;
	}

	public void setStdStoreLocationProvince(String stdStoreLocationProvince) {
		this.stdStoreLocationProvince = stdStoreLocationProvince;
	}

	public String getStdStoreLocationCity() {
		return stdStoreLocationCity;
	}

	public void setStdStoreLocationCity(String stdStoreLocationCity) {
		this.stdStoreLocationCity = stdStoreLocationCity;
	}

	public String getStdCityCode() {
		return stdCityCode;
	}

	public void setStdCityCode(String stdCityCode) {
		this.stdCityCode = stdCityCode;
	}

	public String getStdDeliveryCityCode() {
		return stdDeliveryCityCode;
	}

	public void setStdDeliveryCityCode(String stdDeliveryCityCode) {
		this.stdDeliveryCityCode = stdDeliveryCityCode;
	}

	public String getStdStoreLocationCityCode() {
		return stdStoreLocationCityCode;
	}

	public void setStdStoreLocationCityCode(String stdStoreLocationCityCode) {
		this.stdStoreLocationCityCode = stdStoreLocationCityCode;
	}


	public String getStdProvince() {
		return stdProvince;
	}

	public void setStdProvince(String stdProvince) {
		this.stdProvince = stdProvince;
	}

	public String getStdCity() {
		return stdCity;
	}

	public void setStdCity(String stdCity) {
		this.stdCity = stdCity;
	}

	public String getStdDeliveryProvince() {
		return stdDeliveryProvince;
	}

	public void setStdDeliveryProvince(String stdDeliveryProvince) {
		this.stdDeliveryProvince = stdDeliveryProvince;
	}

	public String getStdDeliveryCity() {
		return stdDeliveryCity;
	}

	public void setStdDeliveryCity(String stdDeliveryCity) {
		this.stdDeliveryCity = stdDeliveryCity;
	}

	public String getStdProductPrice() {
		return stdProductPrice;
	}

	public void setStdProductPrice(String stdProductPrice) {
		this.stdProductPrice = stdProductPrice;
	}

	public String getStdProductPromPrice() {
		return stdProductPromPrice;
	}

	public void setStdProductPromPrice(String stdProductPromPrice) {
		this.stdProductPromPrice = stdProductPromPrice;
	}

	public String getDataID() {
		return dataID;
	}

	public void setDataID(String dataID) {
		this.dataID = dataID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}



	public String getDeliveryStartArea() {
		return deliveryStartArea;
	}

	public void setDeliveryStartArea(String deliveryStartArea) {
		this.deliveryStartArea = deliveryStartArea;
	}

	public String getStoreLocation() {
		return storeLocation;
	}

	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductPromPrice() {
		return productPromPrice;
	}

	public void setProductPromPrice(String productPromPrice) {
		this.productPromPrice = productPromPrice;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getStoreURL() {
		return storeURL;
	}

	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}



}
