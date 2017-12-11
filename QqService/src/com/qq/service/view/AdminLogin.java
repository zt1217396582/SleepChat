package com.qq.service.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
		
		new AdminLogin();
	}
	
	
	public AdminLogin(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 293, 147);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IDLabel = new JLabel("用户名：");
		IDLabel.setBounds(35, 13, 60, 18);
		contentPane.add(IDLabel);
		
		JLabel passwdLabel = new JLabel("密码：");
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
		
		LoginButton = new JButton("登陆");
		LoginButton.setBounds(38, 71, 92, 27);
		contentPane.add(LoginButton);
		LoginButton.addActionListener(this);
		this.getRootPane().setDefaultButton(LoginButton);
		
		ReigsterButton = new JButton("注册");
		ReigsterButton.setBounds(144, 71, 92, 27);
		contentPane.add(ReigsterButton);
		ReigsterButton.addActionListener(this);
		
		this.setTitle("管理员登陆");
		this.setVisible(true);
		this.setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==LoginButton){
			
		}
		if(e.getSource()==ReigsterButton){
			sh = new SqlHelper();
			String AdminName = IDTextField.getText().trim();
			String AdminPassword = new String(passwdField.getPassword());
			//String data[]={"",AdminName,AdminPassword};
			String paras[] = {AdminName,AdminPassword};
			rs = sh.searchSqlData(SqlCommandList.searchAdministrator, paras);
			sh.addSqlData(SqlCommandList.addAdministrator, paras, rs);
			System.out.println("管理员注册成功！");
		}
	}
}
