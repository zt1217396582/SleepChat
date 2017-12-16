package com.qq.service.controller;

import java.awt.Font;
import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.JTextArea;

import com.qq.service.db.SqlCommandList;
import com.qq.service.db.SqlHelper;
import com.qq.service.model.DataTable;

//对表进行创建管理，处理即时添加删除刷新等功能
public class TableManager {

	public static DataTable onlineUser;
	public static DataTable allUser;
	public static DataTable UserData;
	public static DataTable adminUser;
	
	public static JTextArea logManagement;
	
	SqlHelper sqlHelper;
	ResultSet resultSet;
	
	public DataTable createOnlineUserTable()
	{
		 
		String[] paras = {};
		// 创建SqlHelper对象
		sqlHelper = new SqlHelper();
		resultSet = sqlHelper.searchSqlData(SqlCommandList.searchOnlineUser, paras);
		onlineUser = DataTable.createTable(resultSet);
		
		return onlineUser;
	}
	
	public DataTable createAllUserTable()
	{
		String[] paras = {};
		 // 创建SqlHelper对象
		sqlHelper = new SqlHelper();
		resultSet = sqlHelper.searchSqlData(SqlCommandList.searchAllUser, paras);
		allUser = DataTable.createTable(resultSet);
		
		return allUser;
	}
	
	public DataTable createUserDataTable()
	{
		 String[] paras = {};
		 // 创建SqlHelper对象
		 sqlHelper = new SqlHelper();
		 resultSet = sqlHelper.searchSqlData(SqlCommandList.searchUserData, paras);
		 UserData = DataTable.createTable(resultSet);
		 UserData.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 //调整列宽
		 UserData.jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		 UserData.jTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		 UserData.jTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		 UserData.jTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		 UserData.jTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		 UserData.jTable.getColumnModel().getColumn(5).setPreferredWidth(140);
		 UserData.jTable.getColumnModel().getColumn(6).setPreferredWidth(80);
		 UserData.jTable.getColumnModel().getColumn(7).setPreferredWidth(40);
		 UserData.jTable.getColumnModel().getColumn(8).setPreferredWidth(200);
		 
		 return UserData;
	}
	
	public DataTable createAdminUserTable()
	{
		String[] paras = {};
		// 创建SqlHelper对象
		sqlHelper = new SqlHelper();
		resultSet = sqlHelper.searchSqlData(SqlCommandList.searchAdministrator, paras);
		adminUser = DataTable.createTable(resultSet);
		
		return adminUser;
	}
	
	public JTextArea createLogManagementTextArea()
	{
		logManagement=new JTextArea(22,65);
		logManagement.setFont(new Font("黑体", Font.BOLD, 16));
		logManagement.setEditable(false);
		logManagement.setLineWrap(true);// 激活自动换行功能  
		logManagement.setWrapStyleWord(true);// 激活断行不断字功能 
	 
		return logManagement;
	}
	
}
