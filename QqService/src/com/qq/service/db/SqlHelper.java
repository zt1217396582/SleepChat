/**
 * 提供对数据库进行增删改查操作的方法类
 */

package com.qq.service.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper {
	//声明Connection对象
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static Statement sm=null;

	// 创建statement类对象，用于执行SQL语句
	// Statement statement=new
	// ServiceConDatabase().getSqlConfigration().con.createStatement();;
	// 要执行的SQL语句
	// String sql=null;
	// ResultSet类，用来存放获取的结果集
	// ResultSet rs = null;
	public SqlHelper() {}

	//添加功能
	public void addSqlData(String sql,String[] parameteres,ResultSet rSet) {
		try {
			ResultSetMetaData rsmd = rSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			//ps=con.prepareStatemen("insert into 在线用户(帐号,密码,用户名) "+values(?,?,?))
			String values="values(";
			for(int i=1;i<=columnCount;i++){	
				if(i==1){
					values=values+"?";
				}
				else{
					values=values+",?";
				}
			}
			values=values+")";
			con=ServiceConDatabase.getConnection();
			ps=con.prepareStatement(sql+values);
			
			//插入数据
			/*for(int i=1;i<=columnCount;i++){
				ps.setObject(i, data[i]);
			}*/
			
			//给问号赋值  
            if (parameteres != null) {  
                for (int i = 0; i < parameteres.length; i++) {  
                    ps.setObject(i + 1, parameteres[i]);  
                }  
            }  
            if (ps.executeUpdate() == 1) {  
                System.out.println("插入成功！"); 
                ps.close();
            }
            else{
            	System.out.println("插入失败！！！"); 
            	ps.close();
            }
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	//删除功能
	public void deleteSqlData(String sql,String[] parameteres) {
		try {
			con=ServiceConDatabase.getConnection();
			ps=con.prepareStatement(sql);
			
			//给问号赋值  
            if (parameteres != null) {  
                for (int i = 0; i < parameteres.length; i++) {  
                    ps.setObject(i + 1, parameteres[i]);  
                }  
            }  
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	//修改功能
	public void updateData(String sql,String[] parameteres ){
		try {
			con=ServiceConDatabase.getConnection();
			ps=con.prepareStatement(sql);
			
			//给问号赋值  
            if (parameteres != null) {  
                for (int i = 0; i < parameteres.length; i++) {  
                    ps.setObject(i + 1, parameteres[i]);  
                }  
            }  
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	//客户端传输增，删，改
	public void excuteData(String sql){
		try {
			con=ServiceConDatabase.getConnection();
			sm=con.createStatement();
			sm.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	
	// 查询数据库功能
	public ResultSet searchSqlData(String sql, String[] parameters) {
		try {
			con = ServiceConDatabase.getConnection();
			ps = con.prepareStatement(sql);

			// 给sql语句中的问号赋值
			/*if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setObject(i + 1, parameters[i]);
				}
			}*/

			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}/*finally{
			ServiceConDatabase.close(rs, ps, con);
		}*/
		return rs;
	}
	
}
