/**
 * ��������ӵ����ݿ�ķ���
 */

package com.qq.service.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class ServiceConDatabase {
	// ������������Ҫ�ı���
	private static Connection con = null;

	// �����������ݿ�����Ҫ�Ĳ���
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";

	// �����ȡ�����ļ�����Ҫ�ı���
	//private static MySqlConfigration mySqlConfigration = null;

	// ������������
	static {
		try {
			// ��ȡ����
			//mySqlConfigration = new MySqlConfigration();
			url = MySqlConfigration.getUrl();
			driver = MySqlConfigration.getDriver();
			username = MySqlConfigration.getUserName();
			password = MySqlConfigration.getPassWord();

			// �����������
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			// ���ݿ������쳣����
			System.out.println("�����쳣�����ݿ����ʧ��");
			e.printStackTrace();
		}
	}

	/* �������ݿ� */
	public static Connection getConnection() {
		try {
			// ��������
			con = DriverManager.getConnection(url, username, password);
			if (!con.isReadOnly()) {
				System.out.println("�ɹ����ӵ����ݿ�");
			} else {
				System.out.println("�������ݿ�ʧ��");
			}
		} catch (Exception e) {
			System.out.println("���ݿ�����ʧ�ܣ�");
			e.printStackTrace();
		}

		return con;
	}

	/*�ر���Դ*/
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
	
	
	// ����statement���������ִ��SQL���
	// Statement statement = mySqlConfigration.con.createStatement();
	// Ҫִ�е�SQL���
	// String sql = "select * from emp";
	// 3.ResultSet�࣬������Ż�ȡ�Ľ��������
	// ResultSet rs = statement.executeQuery(sql);
	/*
	 * System.out.println("-----------------"); System.out.println("ִ�н��������ʾ:");
	 * System.out.println("-----------------"); System.out.println("����" + "\t" +
	 * "ְ��"); System.out.println("-----------------");
	 * 
	 * String job = null; String id = null; while(rs.next()){ //��ȡstuname��������
	 * job = rs.getString("job"); //��ȡstuid�������� id = rs.getString("ename");
	 * 
	 * //������ System.out.println(id + "\t" + job); }
	 * 
	 * System.out.println("���ݿ����ݻ�ȡ�ɹ�"); rs.close();
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		 new ServiceConDatabase();
	}

	public ServiceConDatabase() {
	}

}
