/**
 * mysql���������
 */

package com.qq.service.db;

import java.sql.Connection;

public class MySqlConfigration {
	//����connection����
	static Connection con;
	//��������������
	private static String driver = "com.mysql.jdbc.Driver";
	//urlָ��Ҫ���ʵ����ݿ�
	private static String url = "jdbc:mysql://localhost:3306/qq?characterEncoding=utf8&useSSL=true";
	//MySQL����ʱ���û���
	private static String username = "root";
	//MySQL����ʱ������
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
