package com.louis.util;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DemoDBCP {
	
	// 连接数据库的参数
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://202.202.5.140:3306/test";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "1701";

    // 创建连接池
    public static BasicDataSource dataSource = new BasicDataSource();

    // 静态代码块
    static {
        // 对连接池对象进行基本的配置
        dataSource.setDriverClassName(DRIVER);     // 待连接的数据库的驱动
        dataSource.setUrl(URL);                 // 指定要连接的数据库的地址
        dataSource.setUsername(USERNAME);         // 指定要连接的用户名
        dataSource.setPassword(PASSWORD);          // 指定要连接数据库的密码

        // 这里应该还有设置连接数的语句
    }

    // 返回连接池对象
    public static DataSource getDataSource() {
        return dataSource;
    }
}
