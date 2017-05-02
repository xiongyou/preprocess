## dataPreprocess

数据预处理
2016.12.28版本：
新增功能:将旧表中的error行信息另存放到一张新表中。

使用步骤：
1、在数据库test中新建一个个表，例如原来的表是data12,新建表命名为data12_new，两个表的存储结构一样
2、后台处理process.java中修改表的名字：newTableName = "新表名";oldTableName = "旧表名";errorProductName = "错误信息表名";
3、运行main函数

/*
Navicat MySQL Data Transfer

Source Server         : 202.202.5.140_3306
Source Server Version : 50626
Source Host           : 202.202.5.140:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-03-24 11:06:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data
-- ----------------------------
DROP TABLE IF EXISTS `data`;
CREATE TABLE `data` (
  `dataID` int(11) NOT NULL AUTO_INCREMENT,
  `productInnerId` int(11) DEFAULT NULL,
  `productActualID` varchar(255) DEFAULT NULL,
  `productURL` varchar(350) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `productDescription` varchar(2550) DEFAULT NULL,
  `shelveDate` varchar(255) DEFAULT NULL,
  `weight` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `specialtyCategory` varchar(255) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `factoryName` varchar(255) DEFAULT NULL,
  `factoryAddress` varchar(255) DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `specification` varchar(255) DEFAULT NULL,
  `deliveryStartArea` varchar(255) DEFAULT NULL,
  `productPrice` varchar(255) DEFAULT NULL,
  `productPromPrice` varchar(255) DEFAULT NULL,
  `monthSaleCount` varchar(255) DEFAULT NULL,
  `commentCount` varchar(255) DEFAULT NULL,
  `storeActualID` varchar(255) DEFAULT NULL,
  `storeName` varchar(255) DEFAULT NULL,
  `storeURL` varchar(255) DEFAULT NULL,
  `shopkeeper` varchar(255) DEFAULT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `storeLocation` varchar(255) DEFAULT NULL,
  `expirationDay` varchar(255) DEFAULT NULL,
  `produceDay` varchar(255) DEFAULT NULL,
  `productPromotionID` varchar(255) DEFAULT NULL,
  `productCompleteID` varchar(255) DEFAULT NULL,
  `category1` varchar(255) DEFAULT NULL,
  `category2` varchar(255) DEFAULT NULL,
  `category3` varchar(255) DEFAULT NULL,
  `goodCommentCount` varchar(255) DEFAULT NULL,
  `midCommentCount` varchar(255) DEFAULT NULL,
  `badCommentCount` varchar(255) DEFAULT NULL,
  `SaleCount` varchar(255) DEFAULT NULL,
  `goodCommentRate` varchar(255) DEFAULT NULL,
  `category4` varchar(255) DEFAULT NULL,
  `taxRate` varchar(255) DEFAULT NULL,
  `productHighVipPrice` varchar(255) DEFAULT NULL,
  `productLowVipPrice` varchar(255) DEFAULT NULL,
  `productTax` varchar(255) DEFAULT NULL,
  `pictureCommentCount` varchar(255) DEFAULT NULL,
  `additionCommentCount` varchar(255) DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `extractTime` datetime DEFAULT NULL,
  `analyzeTime` datetime DEFAULT NULL,
  `errorInfo` varchar(255) DEFAULT NULL,
  `TaskDataID` int(11) DEFAULT NULL,
  `std_stdProductPrice` decimal(15,3) DEFAULT NULL,
  `std_stdProductPromPrice` decimal(15,3) DEFAULT NULL,
  `std_stdPrice` decimal(15,3) DEFAULT NULL,
  `std_stdWeightValue` decimal(15,3) DEFAULT NULL,
  `std_province` varchar(255) DEFAULT NULL,
  `std_provinceCode` int(11) DEFAULT NULL,
  `std_city` varchar(255) DEFAULT NULL,
  `std_cityCode` int(11) DEFAULT NULL,
  `productLocationCode` int(11) DEFAULT NULL,
  `std_deliveryProvince` varchar(255) DEFAULT NULL,
  `std_deliveryProvinceCode` int(11) DEFAULT NULL,
  `std_deliveryCity` varchar(255) DEFAULT NULL,
  `std_deliveryCityCode` int(11) DEFAULT NULL,
  `std_storeLocationProvince` varchar(255) DEFAULT NULL,
  `std_storeLocationProvinceCode` int(11) DEFAULT NULL,
  `std_storeLocationCity` varchar(255) DEFAULT NULL,
  `std_storeLocationCityCode` int(11) DEFAULT NULL,
  `storeLocationCode` int(11) DEFAULT NULL,
  `std_storeURL` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `monthOfYear` int(11) DEFAULT NULL,
  `dayOfMonth` int(11) DEFAULT NULL,
  `isValid` int(11) DEFAULT '1',
  `isOperation` int(11) DEFAULT '0',
  `relativeInnerId` int(11) DEFAULT NULL,
  PRIMARY KEY (`dataID`),
  KEY `sid` (`storeActualID`) USING BTREE,
  KEY `p_inner_id` (`productInnerId`) USING BTREE,
  KEY `pid` (`productActualID`) USING BTREE,
  KEY `tid` (`TaskDataID`) USING BTREE,
  KEY `error` (`errorInfo`),
  KEY `website` (`website`)
) ENGINE=InnoDB AUTO_INCREMENT=600390 DEFAULT CHARSET=utf8;

