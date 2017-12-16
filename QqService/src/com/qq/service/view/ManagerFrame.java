/**
 * 后台数据窗口及标签
 */

package com.qq.service.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.qq.service.controller.TableManager;
import com.qq.service.db.SqlCommandList;
import com.qq.service.db.SqlHelper;
import com.qq.service.model.DataTable;

public class ManagerFrame extends JFrame implements ChangeListener, ListSelectionListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6875613187779417292L;
	
	DataTable dataTable;
	TableManager tableManager;
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
		
		//创建各表
		tableManager = new TableManager();
		tableManager.createOnlineUserTable();
		tableManager.createAllUserTable();
		tableManager.createUserDataTable();
		tableManager.createAdminUserTable();
		
		
		OnlineUser = new JPanel();
		OnlineUser.setName("在线用户面板");
		OnlineUser.add(TableManager.onlineUser, BorderLayout.CENTER);
		
		jTabbedPane.add("在线用户", OnlineUser);
		jTabbedPane.add("全部用户", AllUser);
		jTabbedPane.add("用户资料", UserData);
		jTabbedPane.add("管理员帐户", AdminUser);
		jTabbedPane.add("日志管理", LogManagement);
		jTabbedPane.addChangeListener(this);

		
		JScrollPane scroll = new JScrollPane(tableManager.createLogManagementTextArea());
		scroll.setHorizontalScrollBarPolicy(  
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
				scroll.setVerticalScrollBarPolicy(  
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		LogManagement.add(scroll);
		
		
		adminPanel = new AdminPanel();
		adminPanel.admin.setVisible(false);
		
		getContentPane().add(jTabbedPane, "Center");		

		getContentPane().add(adminPanel.admin, "South");
		
		this.setSize(650, 550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

	}
	
	//管理员登录面板
	protected class AdminPanel extends JFrame
	{
		 /**
		 * 
		 */
		private static final long serialVersionUID = 3449512980886572705L;
		
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
			tableManager.createOnlineUserTable();
			OnlineUser.add(TableManager.onlineUser, BorderLayout.CENTER);
         }
		//总用户标签
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 1) {
			 AllUser.removeAll();
			 adminPanel.admin.setVisible(false);
			 tableManager.createAllUserTable();
			 AllUser.add(TableManager.allUser, BorderLayout.CENTER);
         }
		//用户资料标签
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 2) {
			 UserData.removeAll();
			 adminPanel.admin.setVisible(false);
			 tableManager.createUserDataTable();
			 UserData.add(TableManager.UserData, BorderLayout.CENTER);
         }
		//管理员帐户标签
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 3) {
			 AdminUser.removeAll();
			 
			 tableManager.createAdminUserTable();
			 //创建被选中行的事件监听器
			 ListSelectionModel selectModel = TableManager.adminUser.jTable.getSelectionModel();
			 selectModel.addListSelectionListener(this);
			 
			 // 监听按钮
			 adminPanel.ChangejButton.addActionListener(this);
			 adminPanel.AddjButton.addActionListener(this);
			 adminPanel.DeletejButton.addActionListener(this);
			 
			 AdminUser.add(TableManager.adminUser, BorderLayout.CENTER);
		     adminPanel.admin.setVisible(true);
         }
		//日志管理标签
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 4){
			 adminPanel.admin.setVisible(false); 
		 }
	}


	public void valueChanged(ListSelectionEvent e) {
		// TODO 自动生成的方法存根

	}


	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
		sh = new SqlHelper();
	    
		//点击修改密码按钮
		if(arg0.getSource()==adminPanel.ChangejButton){
			selectedRow = TableManager.adminUser.jTable.getSelectedRow();//获得选中行
			if(selectedRow!=-1)
			{
				String tableId=(TableManager.adminUser.tableModel.getValueAt(selectedRow, 0)).toString();
			    
				String id=adminPanel.IdTextField.getText().trim();
				String passwd=adminPanel.passwdTextField.getText().trim();
				String paras[]={passwd,id};
				if(id.equals(tableId))
				{
					TableManager.adminUser.tableModel.setValueAt(passwd, selectedRow, 1);
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
			TableManager.adminUser.tableModel.addRow(paras);
			
			adminPanel.IdTextField.setText("");
			adminPanel.passwdTextField.setText("");
		}
		//点击删除帐号按钮
		if(arg0.getSource()==adminPanel.DeletejButton){
			selectedRow = TableManager.adminUser.jTable.getSelectedRow();//获得选中行
			if(selectedRow!=-1)
			{
				String tableId=(TableManager.adminUser.tableModel.getValueAt(selectedRow, 0)).toString();
				String paras[]={tableId};
				sh.deleteSqlData(SqlCommandList.deleteAdministrator,paras);
				TableManager.adminUser.tableModel.removeRow(selectedRow);
				
			}
		}
	}

}
