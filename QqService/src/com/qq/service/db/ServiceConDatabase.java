/**
 * 服务端连接到数据库的方法
 */

package com.qq.service.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class ServiceConDatabase {
	// 定义链接所需要的变量
	private static Connection con = null;

	// 定义链接数据库所需要的参数
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";

	// 定义读取配置文件所需要的变量
	//private static MySqlConfigration mySqlConfigration = null;

	// 加载驱动程序
	static {
		try {
			// 读取配置
			//mySqlConfigration = new MySqlConfigration();
			url = MySqlConfigration.getUrl();
			driver = MySqlConfigration.getDriver();
			username = MySqlConfigration.getUserName();
			password = MySqlConfigration.getPassWord();

			// 驱动程序加载
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			// 数据库驱动异常处理
			System.out.println("驱动异常，数据库加载失败");
			e.printStackTrace();
		}
	}

	/* 连接数据库 */
	public static Connection getConnection() {
		try {
			// 建立连接
			con = DriverManager.getConnection(url, username, password);
			if (!con.isReadOnly()) {
				System.out.println("成功连接到数据库");
			} else {
				System.out.println("连接数据库失败");
			}
		} catch (Exception e) {
			System.out.println("数据库链接失败！");
			e.printStackTrace();
		}

		return con;
	}

	/*关闭资源*/
	public static void close(ResultSet rs,Statement ps, Connection con){  
        if(rs != null) {  
            try {  
                rs.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        if(ps != null) {  
            try {  
                ps.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        if(con != null) {  
            try {  
                con.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
	
	
	// 创建statement类对象，用于执行SQL语句
	// Statement statement = mySqlConfigration.con.createStatement();
	// 要执行的SQL语句
	// String sql = "select * from emp";
	// 3.ResultSet类，用来存放获取的结果集！！
	// ResultSet rs = statement.executeQuery(sql);
	/*
	 * System.out.println("-----------------"); System.out.println("执行结果如下所示:");
	 * System.out.println("-----------------"); System.out.println("姓名" + "\t" +
	 * "职称"); System.out.println("-----------------");
	 * 
	 * String job = null; String id = null; while(rs.next()){ //获取stuname这列数据
	 * job = rs.getString("job"); //获取stuid这列数据 id = rs.getString("ename");
	 * 
	 * //输出结果 System.out.println(id + "\t" + job); }
	 * 
	 * System.out.println("数据库数据获取成功"); rs.close();
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		 new ServiceConDatabase();
	}

	public ServiceConDatabase() {
	}

}
