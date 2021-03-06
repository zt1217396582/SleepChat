/**
 * 在managerFrame窗口创建单个表的方法
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
	
	private Vector<String> columnName;// 表格的列名数组
	private Vector<Vector<Object>> tableValue;// 表格的数据数组
	public JTable jTable;// 移动列表格对象
	public DefaultTableModel tableModel;// 移动列表格模型对象
	public int selectedRow;
	
	public DataTable() {}
	public DataTable(Vector<String> columnName, Vector<Vector<Object>> tableValue) {
		super();
		setLayout(new BorderLayout());
		this.columnName = columnName;// 表格列名数组
		this.tableValue = tableValue;// 表格数据数组
		// 创建移动列表格
		tableModel = new DefaultTableModel(tableValue, columnName);// 创建可移动列表格模型对象
		//创建可移动列表格对象,让表格可被选中但不能被编辑
		jTable = new JTable(tableModel) 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 4096101796269829143L;

			public boolean isCellEditable(int row, int column)
			{
				return false;
            }//表格不允许被编辑
		};
		//设置表格大小
		jTable.setPreferredScrollableViewportSize(new Dimension(600,400));
		
		// 关闭表格的自动调整功能
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// 获得选择模型对象
		ListSelectionModel selectModel = jTable.getSelectionModel();
		// 选择模式为单选
		selectModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// 创建滚动面板
		JScrollPane scrollPane = new JScrollPane();// 创建一个滚动面板对象
		JViewport viewport = new JViewport();
		
		// 设置视口首选大小
		scrollPane.setRowHeaderView(viewport);
		scrollPane.setViewportView(jTable);	
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.updateUI();
		add(scrollPane, BorderLayout.CENTER);
	}	
	
	// 创建表
	public static DataTable createTable(ResultSet rs)
	{
		Vector<String> columnName = new Vector<String>();
		Vector<Vector<Object>> tableValue = new Vector<Vector<Object>>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			// 创建列名向量
			Vector<Object> rowValue = null;// 创建列数据向量
			//添加列名
			for(int i = 1; i <= columnCount; i++) {
				columnName.add(rsmd.getColumnName(i));
			}
			//添加从数据库读取的数据列表
			while(rs.next())
			{
				rowValue = new Vector<Object>();
				for(int i = 1; i <= columnCount; i++) {
					rowValue.add(rs.getString(i));
				}				
				tableValue.add(rowValue);
			}
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		// 把行向量添加到数据向量
		// 创建面板，在该面板中实现带行标题栏的表格
		DataTable dataTable = new DataTable(columnName, tableValue);
		return dataTable;
	}
	
	public void setRowCount(int i) {
		// TODO 自动生成的方法存根
		tableModel.setRowCount(i);
	}
	
	public int getRowCount() {// 返回行数
		return tableValue.size();
	}
	
	public int getColumnCount() {// 返回可移动列的数量
		return columnName.size();// 返回列的数量
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {// 返回指定单元格的值
		return tableValue.get(rowIndex).get(columnIndex);
	}
	
	public String getColumnName(int columnIndex) { // 返回指定列的名称
		return columnName.get(columnIndex);
	}
}

