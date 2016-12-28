## dataPreprocess

数据预处理
2016.12.28版本：
新增功能:将旧表中的error行信息另存放到一张新表中。

使用步骤：
1、在数据库test中新建一个个表，例如原来的表是data12,新建表命名为data12_new，两个表的存储结构一样
2、后台处理process.java中修改表的名字：newTableName = "新表名";oldTableName = "旧表名";errorProductName = "错误信息表名";
3、运行main函数


