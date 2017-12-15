/**
 * �ṩ�����ݿ������ɾ�Ĳ�����ķ�����
 */

package com.qq.service.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper {
	//����Connection����
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static Statement sm=null;

	// ����statement���������ִ��SQL���
	// Statement statement=new
	// ServiceConDatabase().getSqlConfigration().con.createStatement();;
	// Ҫִ�е�SQL���
	// String sql=null;
	// ResultSet�࣬������Ż�ȡ�Ľ����
	// ResultSet rs = null;
	public SqlHelper() {}

	//��ӹ���
	public void addSqlData(String sql,String[] parameteres,ResultSet rSet) {
		try {
			ResultSetMetaData rsmd = rSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			//ps=con.prepareStatemen("insert into �����û�(�ʺ�,����,�û���) "+values(?,?,?))
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
			
			//��������
			/*for(int i=1;i<=columnCount;i++){
				ps.setObject(i, data[i]);
			}*/
			
			//���ʺŸ�ֵ  
            if (parameteres != null) {  
                for (int i = 0; i < parameteres.length; i++) {  
                    ps.setObject(i + 1, parameteres[i]);  
                }  
            }  
            if (ps.executeUpdate() == 1) {  
                System.out.println("����ɹ���"); 
                ps.close();
            }
            else{
            	System.out.println("����ʧ�ܣ�����"); 
            	ps.close();
            }
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	//ɾ������
	public void deleteSqlData(String sql,String[] parameteres) {
		try {
			con=ServiceConDatabase.getConnection();
			ps=con.prepareStatement(sql);
			
			//���ʺŸ�ֵ  
            if (parameteres != null) {  
                for (int i = 0; i < parameteres.length; i++) {  
                    ps.setObject(i + 1, parameteres[i]);  
                }  
            }  
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	//�޸Ĺ���
	public void updateData(String sql,String[] parameteres ){
		try {
			con=ServiceConDatabase.getConnection();
			ps=con.prepareStatement(sql);
			
			//���ʺŸ�ֵ  
            if (parameteres != null) {  
                for (int i = 0; i < parameteres.length; i++) {  
                    ps.setObject(i + 1, parameteres[i]);  
                }  
            }  
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	//�ͻ��˴�������ɾ����
	public void excuteData(String sql){
		try {
			con=ServiceConDatabase.getConnection();
			sm=con.createStatement();
			sm.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	
	// ��ѯ���ݿ⹦��
	public ResultSet searchSqlData(String sql, String[] parameters) {
		try {
			con = ServiceConDatabase.getConnection();
			ps = con.prepareStatement(sql);

			// ��sql����е��ʺŸ�ֵ
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
