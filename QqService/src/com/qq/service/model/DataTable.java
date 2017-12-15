/**
 * ��managerFrame���ڴ���������ķ���
 */

package com.qq.service.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class DataTable extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1689590288583562354L;
	
	private Vector<String> columnName;// ������������
	private Vector<Vector<Object>> tableValue;// ������������
	public JTable jTable;// �ƶ��б�����
	public DefaultTableModel tableModel;// �ƶ��б��ģ�Ͷ���
	public int selectedRow;
	
	public DataTable() {}
	public DataTable(Vector<String> columnName, Vector<Vector<Object>> tableValue) {
		super();
		setLayout(new BorderLayout());
		this.columnName = columnName;// �����������
		this.tableValue = tableValue;// �����������
		// �����ƶ��б��
		tableModel = new DefaultTableModel(tableValue, columnName);// �������ƶ��б��ģ�Ͷ���
		//�������ƶ��б�����,�ñ��ɱ�ѡ�е����ܱ��༭
		jTable = new JTable(tableModel) 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 4096101796269829143L;

			public boolean isCellEditable(int row, int column)
			{
				return false;
            }//��������༭
		};
		//���ñ���С
		jTable.setPreferredScrollableViewportSize(new Dimension(600,400));
		
		// �رձ����Զ���������
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// ���ѡ��ģ�Ͷ���
		ListSelectionModel selectModel = jTable.getSelectionModel();
		// ѡ��ģʽΪ��ѡ
		selectModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// �����������
		JScrollPane scrollPane = new JScrollPane();// ����һ������������
		JViewport viewport = new JViewport();
		
		// �����ӿ���ѡ��С
		scrollPane.setRowHeaderView(viewport);
		scrollPane.setViewportView(jTable);	
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.updateUI();
		add(scrollPane, BorderLayout.CENTER);
	}	
	
	// ������
	public static DataTable createTable(ResultSet rs)
	{
		Vector<String> columnName = new Vector<String>();
		Vector<Vector<Object>> tableValue = new Vector<Vector<Object>>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			// ������������
			Vector<Object> rowValue = null;// ��������������
			//�������
			for(int i = 1; i <= columnCount; i++) {
				columnName.add(rsmd.getColumnName(i));
			}
			//��Ӵ����ݿ��ȡ�������б�
			while(rs.next())
			{
				rowValue = new Vector<Object>();
				for(int i = 1; i <= columnCount; i++) {
					rowValue.add(rs.getString(i));
				}				
				tableValue.add(rowValue);
			}
			
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

		// ����������ӵ���������
		// ������壬�ڸ������ʵ�ִ��б������ı��
		DataTable dataTable = new DataTable(columnName, tableValue);
		return dataTable;
	}
	
	public void setRowCount(int i) {
		// TODO �Զ����ɵķ������
		tableModel.setRowCount(i);
	}
	
	public int getRowCount() {// ��������
		return tableValue.size();
	}
	
	public int getColumnCount() {// ���ؿ��ƶ��е�����
		return columnName.size();// �����е�����
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {// ����ָ����Ԫ���ֵ
		return tableValue.get(rowIndex).get(columnIndex);
	}
	
	public String getColumnName(int columnIndex) { // ����ָ���е�����
		return columnName.get(columnIndex);
	}
}

