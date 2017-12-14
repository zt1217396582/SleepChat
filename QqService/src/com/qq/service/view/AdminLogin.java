package com.qq.service.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import com.qq.service.db.*;


public class AdminLogin extends JFrame implements ActionListener{
	SqlHelper sh = null;
	ResultSet rs = null;
	
	private JPanel contentPane;
	private JTextField IDTextField;
	private JPasswordField passwdField;
	private JButton LoginButton;
	private JButton ReigsterButton;
	
public static void main(String[] args) {
	try {
		UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
	} catch (Exception e) {
		e.printStackTrace();
	}
		new AdminLogin();
	}
	
	
	public AdminLogin(){
		 // �õ���ʾ����Ļ�Ŀ��
	    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
	    //���ھ�������
	    setBounds((width-293)/2, (height-147)/2, 293, 147);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IDLabel = new JLabel("�ʺţ�");
		IDLabel.setBounds(35, 13, 60, 18);
		contentPane.add(IDLabel);
		
		JLabel passwdLabel = new JLabel("���룺");
		passwdLabel.setBounds(50, 44, 45, 18);
		contentPane.add(passwdLabel);
		
		IDTextField = new JTextField();
		IDTextField.setBounds(109, 10, 127, 24);
		contentPane.add(IDTextField);
		IDTextField.setColumns(10);
		
		passwdField = new JPasswordField();
		passwdField.setBounds(109, 41, 127, 24);
		contentPane.add(passwdField);
		passwdField.setColumns(10);
		
		LoginButton = new JButton("��¼");
		LoginButton.setBounds(38, 71, 92, 27);
		contentPane.add(LoginButton);
		LoginButton.addActionListener(this);
		this.getRootPane().setDefaultButton(LoginButton);
		
		ReigsterButton = new JButton("ע��");
		ReigsterButton.setBounds(144, 71, 92, 27);
		contentPane.add(ReigsterButton);
		ReigsterButton.addActionListener(this);
		
		this.setTitle("����Ա��¼");
		this.setVisible(true);
		this.setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==LoginButton){
			int isFalse=1;
			sh = new SqlHelper();
			String AdminUserID = IDTextField.getText().trim();
			String AdminPassword = new String(passwdField.getPassword());
			String paras[]={AdminUserID,AdminPassword};
			rs = sh.searchSqlData(SqlCommandList.searchAdministrator,paras);
			try {
				while(rs.next()){
					if(AdminUserID.equals(rs.getString(1))){
						if(AdminPassword.equals(rs.getString(2))){
							isFalse=0;
							JOptionPane.showMessageDialog(this, "��¼�ɹ���");
							new ServiceFrame();
							this.dispose();
							break;
						}
					}
				}
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			if(isFalse==1){
				JOptionPane.showMessageDialog(this, "������˺Ŵ�������������!");
				IDTextField.setText("");
				passwdField.setText("");
			}
		}
		if(e.getSource()==ReigsterButton){
			int isFalse=0;
			sh = new SqlHelper();
			String AdminUserID = IDTextField.getText().trim();
			String AdminPassword = new String(passwdField.getPassword());
			//String data[]={"",AdminName,AdminPassword};
			String paras[] = {AdminUserID,AdminPassword};
			rs = sh.searchSqlData(SqlCommandList.searchAdministrator, paras);
			try {
				while(rs.next()){
					if(AdminUserID.equals(rs.getString(1))){
						JOptionPane.showMessageDialog(this, "�Ѵ��ڸ��˺ţ�����������!");
						isFalse=1;
						IDTextField.setText("");
						passwdField.setText("");
						break;
					}
				}
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			if(isFalse!=1){
				sh.addSqlData(SqlCommandList.addAdministrator, paras, rs);
				JOptionPane.showMessageDialog(this, "����Աע��ɹ���");
				this.dispose();
			}
		}
	}
}
