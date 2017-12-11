/**
 * mysql的相关配置
 */

package com.qq.service.db;

import java.sql.Connection;

public class MySqlConfigration {
	//申明connection对象
	static Connection con;
	//创建驱动程序名
	private static String driver = "com.mysql.jdbc.Driver";
	//url指向要访问的数据库
	private static String url = "jdbc:mysql://localhost:3306/qq?characterEncoding=utf8&useSSL=true";
	//MySQL配置时的用户名
	private static String username = "root";
	//MySQL配置时的密码
	private static String password = "zt296949650";
	
	public static void setUserName(String UserName) {
		username = UserName;
	}
	public static void setPassWord(String PassWord) {
		password = PassWord;
	}
	
	public static String getUserName() {
		return username;
	}
	public static String getPassWord() {
		return password;
	}
	public static String getUrl() {
		return url;
	}
	public static String getDriver() {
		return driver;
	}
	public static Connection getConnection() {
		return con;
	}
	

}
