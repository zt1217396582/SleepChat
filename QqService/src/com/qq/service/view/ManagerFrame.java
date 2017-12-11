/**
 * 后台数据窗口及标签
 */

package com.qq.service.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.events.MouseEvent;

import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.jdbc.JDBC4ClientInfoProvider;
import com.qq.service.db.SqlCommandList;
import com.qq.service.db.SqlHelper;
import com.qq.service.model.DataTable;

public class ManagerFrame extends JFrame implements ChangeListener, ListSelectionListener, ActionListener {

	DataTable dataTable;
	static int selectedRow;
	SqlHelper sh = null;
	ResultSet rs = null;

	JPanel contentPane;
	JTabbedPane jTabbedPane;// 创建选项卡窗口管理
	JPanel OnlineUser;// 当前在线用户表格标签
	JPanel AllUser;// 总共用户表格标签
	JPanel UserData;// 用户资料表格标签
	JPanel AdminUser;// 管理员帐户表格标签
	JPanel LogManagement;// 日志管理表格标签
	AdminPanel adminPanel;// 管理员帐号修改界面
	/* 其他表格后续添加 */
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerFrame frame = new ManagerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public ManagerFrame() {

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		setTitle("后台数据");
		jTabbedPane = new JTabbedPane();// 初始化表格控件
		AllUser = new JPanel();
		UserData = new JPanel();
		AdminUser = new JPanel();
		LogManagement = new JPanel();
		
		
		sh = new SqlHelper();
		String[] paras = {};
		rs = sh.searchSqlData(SqlCommandList.searchOnlineUser, paras);
		
		dataTable = DataTable.createTable(rs);

		OnlineUser = new JPanel();
		OnlineUser.setName("在线用户面板");
		OnlineUser.add(dataTable, BorderLayout.CENTER);
		//OnlineUser.add(jb1, BorderLayout.SOUTH);
		
		jTabbedPane.add("在线用户", OnlineUser);
		// OnlineUser.setLayout(null);
		jTabbedPane.add("全部用户", AllUser);
		jTabbedPane.add("用户资料", UserData);
		jTabbedPane.add("管理员帐户", AdminUser);
		jTabbedPane.add("日志文件管理", LogManagement);
		jTabbedPane.addChangeListener(this);

		adminPanel = new AdminPanel();
		adminPanel.admin.setVisible(false);
		
		getContentPane().add(jTabbedPane, "Center");		

		getContentPane().add(adminPanel.admin, "South");
		
		this.setSize(650, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);

	}
	
	protected class AdminPanel extends JFrame
	{
		 JPanel admin=new JPanel();
		 JButton ChangejButton=new JButton("修改密码");
		 JButton AddjButton=new JButton("添加帐号");
		 JButton DeletejButton=new JButton("删除帐号");
		 JLabel userLabel = new JLabel("帐号：");
		 JLabel passwdLabel = new JLabel("密码：");
		 JTextField IdTextField=new JTextField("",10);
		 JTextField passwdTextField =new JTextField("",10);
		 
	     
		 public AdminPanel() {
			// TODO 自动生成的构造函数存根
			 admin.setLayout(new FlowLayout());
			 admin.add(userLabel);
			 admin.add(IdTextField);
			 admin.add(passwdLabel);
			 admin.add(passwdTextField);
			 admin.add(ChangejButton);
			 admin.add(AddjButton);
			 admin.add(DeletejButton);
		}
	}
	


	//点击标签时在不同的表之间切换
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO 自动生成的方法存根
		
		//获取标签编号,按不同的标签创建不同的表
		
		//在线用户标签
		if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 0) {
			//清空旧表
			OnlineUser.removeAll();
			adminPanel.admin.setVisible(false);
			 
			String[] paras = {};
			// 创建SqlHelper对象
			sh = new SqlHelper();
			rs = sh.searchSqlData(SqlCommandList.searchOnlineUser, paras);
			dataTable = DataTable.createTable(rs);
			
			OnlineUser.add(dataTable, BorderLayout.CENTER);
         }
		//总用户标签
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 1) {
			 AllUser.removeAll();
			 adminPanel.admin.setVisible(false);
			 String[] paras = {};
	
			 // 创建SqlHelper对象
			 sh = new SqlHelper();
			 rs = sh.searchSqlData(SqlCommandList.searchAllUser, paras);
			 dataTable = DataTable.createTable(rs);
			 AllUser.add(dataTable, BorderLayout.CENTER);
         }
		//用户资料标签
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 2) {
			 UserData.removeAll();
			 adminPanel.admin.setVisible(false);
			 String[] paras = {};
			 
			 // 创建SqlHelper对象
			 sh = new SqlHelper();
			 rs = sh.searchSqlData(SqlCommandList.searchUserData, paras);
			 dataTable = DataTable.createTable(rs);
			 dataTable.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			 //调整列宽
			 dataTable.jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
			 dataTable.jTable.getColumnModel().getColumn(1).setPreferredWidth(80);
			 dataTable.jTable.getColumnModel().getColumn(2).setPreferredWidth(40);
			 dataTable.jTable.getColumnModel().getColumn(3).setPreferredWidth(40);
			 dataTable.jTable.getColumnModel().getColumn(4).setPreferredWidth(100);
			 dataTable.jTable.getColumnModel().getColumn(5).setPreferredWidth(140);
			 dataTable.jTable.getColumnModel().getColumn(6).setPreferredWidth(80);
			 dataTable.jTable.getColumnModel().getColumn(7).setPreferredWidth(40);
			 dataTable.jTable.getColumnModel().getColumn(8).setPreferredWidth(200);

			 UserData.add(dataTable, BorderLayout.CENTER);
         }
		//管理员帐户标签
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 3) {
			 AdminUser.removeAll();
			 String[] paras = {};
			 
			 // 创建SqlHelper对象
			 sh = new SqlHelper();
			 rs = sh.searchSqlData(SqlCommandList.searchAdministrator, paras);
			 dataTable = DataTable.createTable(rs);
			 
			//创建被选中行的事件监听器
			 ListSelectionModel selectModel = dataTable.jTable.getSelectionModel();
			 selectModel.addListSelectionListener(this);
			 
			 // 监听按钮
			 adminPanel.ChangejButton.addActionListener(this);
			 adminPanel.AddjButton.addActionListener(this);
			 adminPanel.DeletejButton.addActionListener(this);
			 
			 
			 AdminUser.add(dataTable, BorderLayout.CENTER);
		     adminPanel.admin.setVisible(true);
         }
		//日志管理标签
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 4){
			 
		 }
	}


	public void valueChanged(ListSelectionEvent e) {
		// TODO 自动生成的方法存根

/*		if(!e.getValueIsAdjusting()){
			selectedRow = dataTable.jTable.getSelectedRow();//获得选中行
		    System.out.println(selectedRow);
		    Object oa=dataTable.tableModel.getValueAt(selectedRow, 0);
		    Object ob=dataTable.tableModel.getValueAt(selectedRow, 1);
		    adminPanel.IdTextField.setText(oa.toString());
			adminPanel.passwdTextField.setText(ob.toString());
		}*/
	}


	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
	    
		//点击修改密码按钮
		if(arg0.getSource()==adminPanel.ChangejButton){
			selectedRow = dataTable.jTable.getSelectedRow();//获得选中行
			if(selectedRow!=-1)
			{
				String tableId=(dataTable.tableModel.getValueAt(selectedRow, 0)).toString();
			    
				String id=adminPanel.IdTextField.getText().trim();
				String passwd=adminPanel.passwdTextField.getText().trim();
				String paras[]={passwd,id};
				if(id.equals(tableId))
				{
					dataTable.tableModel.setValueAt(passwd, selectedRow, 1);
					sh.updateData(SqlCommandList.updateAdministrator,paras);
				}
	
				adminPanel.IdTextField.setText("");
				adminPanel.passwdTextField.setText("");
			}
		}
		//点击添加帐号按钮
		else if(arg0.getSource()==adminPanel.AddjButton){
			String IdTextField=adminPanel.IdTextField.getText().trim();
			String passwdTextField=adminPanel.passwdTextField.getText().trim();
			String paras[]={IdTextField, passwdTextField};
			
			String addAdmin="insert into 管理员帐户(管理员帐号,管理员密码) values(";
			addAdmin=addAdmin+IdTextField+","+"'"+passwdTextField+"'"+")";
			
			sh.excuteData(addAdmin);
			dataTable.tableModel.addRow(paras);
			
			adminPanel.IdTextField.setText("");
			adminPanel.passwdTextField.setText("");
		}
		//点击删除帐号按钮
		if(arg0.getSource()==adminPanel.DeletejButton){
			selectedRow = dataTable.jTable.getSelectedRow();//获得选中行
			if(selectedRow!=-1)
			{
				String tableId=(dataTable.tableModel.getValueAt(selectedRow, 0)).toString();
				String paras[]={tableId};
				sh.deleteSqlData(SqlCommandList.deleteAdministrator,paras);
				dataTable.tableModel.removeRow(selectedRow);
				
			}
		}
	}

}
