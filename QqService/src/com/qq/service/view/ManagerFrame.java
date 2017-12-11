/**
 * ��̨���ݴ��ڼ���ǩ
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
	JTabbedPane jTabbedPane;// ����ѡ����ڹ���
	JPanel OnlineUser;// ��ǰ�����û�����ǩ
	JPanel AllUser;// �ܹ��û�����ǩ
	JPanel UserData;// �û����ϱ���ǩ
	JPanel AdminUser;// ����Ա�ʻ�����ǩ
	JPanel LogManagement;// ��־�������ǩ
	AdminPanel adminPanel;// ����Ա�ʺ��޸Ľ���
	/* ������������� */
	
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
		
		setTitle("��̨����");
		jTabbedPane = new JTabbedPane();// ��ʼ�����ؼ�
		AllUser = new JPanel();
		UserData = new JPanel();
		AdminUser = new JPanel();
		LogManagement = new JPanel();
		
		
		sh = new SqlHelper();
		String[] paras = {};
		rs = sh.searchSqlData(SqlCommandList.searchOnlineUser, paras);
		
		dataTable = DataTable.createTable(rs);

		OnlineUser = new JPanel();
		OnlineUser.setName("�����û����");
		OnlineUser.add(dataTable, BorderLayout.CENTER);
		//OnlineUser.add(jb1, BorderLayout.SOUTH);
		
		jTabbedPane.add("�����û�", OnlineUser);
		// OnlineUser.setLayout(null);
		jTabbedPane.add("ȫ���û�", AllUser);
		jTabbedPane.add("�û�����", UserData);
		jTabbedPane.add("����Ա�ʻ�", AdminUser);
		jTabbedPane.add("��־�ļ�����", LogManagement);
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
		 JButton ChangejButton=new JButton("�޸�����");
		 JButton AddjButton=new JButton("����ʺ�");
		 JButton DeletejButton=new JButton("ɾ���ʺ�");
		 JLabel userLabel = new JLabel("�ʺţ�");
		 JLabel passwdLabel = new JLabel("���룺");
		 JTextField IdTextField=new JTextField("",10);
		 JTextField passwdTextField =new JTextField("",10);
		 
	     
		 public AdminPanel() {
			// TODO �Զ����ɵĹ��캯�����
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
	


	//�����ǩʱ�ڲ�ͬ�ı�֮���л�
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO �Զ����ɵķ������
		
		//��ȡ��ǩ���,����ͬ�ı�ǩ������ͬ�ı�
		
		//�����û���ǩ
		if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 0) {
			//��վɱ�
			OnlineUser.removeAll();
			adminPanel.admin.setVisible(false);
			 
			String[] paras = {};
			// ����SqlHelper����
			sh = new SqlHelper();
			rs = sh.searchSqlData(SqlCommandList.searchOnlineUser, paras);
			dataTable = DataTable.createTable(rs);
			
			OnlineUser.add(dataTable, BorderLayout.CENTER);
         }
		//���û���ǩ
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 1) {
			 AllUser.removeAll();
			 adminPanel.admin.setVisible(false);
			 String[] paras = {};
	
			 // ����SqlHelper����
			 sh = new SqlHelper();
			 rs = sh.searchSqlData(SqlCommandList.searchAllUser, paras);
			 dataTable = DataTable.createTable(rs);
			 AllUser.add(dataTable, BorderLayout.CENTER);
         }
		//�û����ϱ�ǩ
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 2) {
			 UserData.removeAll();
			 adminPanel.admin.setVisible(false);
			 String[] paras = {};
			 
			 // ����SqlHelper����
			 sh = new SqlHelper();
			 rs = sh.searchSqlData(SqlCommandList.searchUserData, paras);
			 dataTable = DataTable.createTable(rs);
			 dataTable.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			 //�����п�
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
		//����Ա�ʻ���ǩ
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 3) {
			 AdminUser.removeAll();
			 String[] paras = {};
			 
			 // ����SqlHelper����
			 sh = new SqlHelper();
			 rs = sh.searchSqlData(SqlCommandList.searchAdministrator, paras);
			 dataTable = DataTable.createTable(rs);
			 
			//������ѡ���е��¼�������
			 ListSelectionModel selectModel = dataTable.jTable.getSelectionModel();
			 selectModel.addListSelectionListener(this);
			 
			 // ������ť
			 adminPanel.ChangejButton.addActionListener(this);
			 adminPanel.AddjButton.addActionListener(this);
			 adminPanel.DeletejButton.addActionListener(this);
			 
			 
			 AdminUser.add(dataTable, BorderLayout.CENTER);
		     adminPanel.admin.setVisible(true);
         }
		//��־�����ǩ
		 else if(((JTabbedPane)arg0.getSource()).getSelectedIndex() == 4){
			 
		 }
	}


	public void valueChanged(ListSelectionEvent e) {
		// TODO �Զ����ɵķ������

/*		if(!e.getValueIsAdjusting()){
			selectedRow = dataTable.jTable.getSelectedRow();//���ѡ����
		    System.out.println(selectedRow);
		    Object oa=dataTable.tableModel.getValueAt(selectedRow, 0);
		    Object ob=dataTable.tableModel.getValueAt(selectedRow, 1);
		    adminPanel.IdTextField.setText(oa.toString());
			adminPanel.passwdTextField.setText(ob.toString());
		}*/
	}


	public void actionPerformed(ActionEvent arg0) {
		// TODO �Զ����ɵķ������
	    
		//����޸����밴ť
		if(arg0.getSource()==adminPanel.ChangejButton){
			selectedRow = dataTable.jTable.getSelectedRow();//���ѡ����
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
		//�������ʺŰ�ť
		else if(arg0.getSource()==adminPanel.AddjButton){
			String IdTextField=adminPanel.IdTextField.getText().trim();
			String passwdTextField=adminPanel.passwdTextField.getText().trim();
			String paras[]={IdTextField, passwdTextField};
			
			String addAdmin="insert into ����Ա�ʻ�(����Ա�ʺ�,����Ա����) values(";
			addAdmin=addAdmin+IdTextField+","+"'"+passwdTextField+"'"+")";
			
			sh.excuteData(addAdmin);
			dataTable.tableModel.addRow(paras);
			
			adminPanel.IdTextField.setText("");
			adminPanel.passwdTextField.setText("");
		}
		//���ɾ���ʺŰ�ť
		if(arg0.getSource()==adminPanel.DeletejButton){
			selectedRow = dataTable.jTable.getSelectedRow();//���ѡ����
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
