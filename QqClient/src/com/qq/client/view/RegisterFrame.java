package com.qq.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;
import com.qq.common.UserData;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;

public class RegisterFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5626509258328046087L;
	
	private JPanel contentPane;
	private JTextField IDTextField;
	private JPasswordField passwdField;
	private JPasswordField repasswdField;
	private JTextField phoneTextField;
	private JButton yesButton;
	private JButton noButton;

	
	int UserCount=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new RegisterFrame();
	}

	/**
	 * Create the frame.
	 */
	public RegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 293, 234);
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
		
		JLabel rePasswdLabel = new JLabel("确认密码：");
		rePasswdLabel.setBounds(20, 74, 75, 18);
		contentPane.add(rePasswdLabel);
		
		JLabel phoneLabel = new JLabel("手机号：");
		phoneLabel.setBounds(35, 105, 60, 18);
		contentPane.add(phoneLabel);
		
		IDTextField = new JTextField();
		IDTextField.setBounds(109, 10, 127, 24);
		contentPane.add(IDTextField);
		IDTextField.setColumns(10);
		
		passwdField = new JPasswordField();
		passwdField.setBounds(109, 41, 127, 24);
		contentPane.add(passwdField);
		passwdField.setColumns(10);
		
		repasswdField = new JPasswordField();
		repasswdField.setBounds(109, 72, 127, 24);
		contentPane.add(repasswdField);
		repasswdField.setColumns(10);
		
		phoneTextField = new JTextField();
		phoneTextField.setBounds(109, 102, 127, 24);
		contentPane.add(phoneTextField);
		phoneTextField.setColumns(10);
		
		yesButton = new JButton("确定");
		yesButton.setBounds(38, 147, 92, 27);
		contentPane.add(yesButton);
		yesButton.addActionListener(this);
		this.getRootPane().setDefaultButton(yesButton);
		
		noButton = new JButton("取消");
		noButton.setBounds(144, 147, 92, 27);
		contentPane.add(noButton);
		noButton.addActionListener(this);
		
		this.setTitle("注册帐号");
		this.setVisible(true);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
		if(arg0.getSource() == yesButton) {
			
			Message message = getRegiserMassage();
			
			//建立连接，发送注册信息
			Socket socket;
			try {
				socket = new Socket("127.0.0.1", 9999);
				ObjectOutputStream objectOutputStream;
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(message);
				
				//等待数据库返回注册成功的信息，然后关闭注册界面，显示注册成功的对话框
				ObjectInputStream objectIntputStream;
				objectIntputStream = new ObjectInputStream(socket.getInputStream());
				Message messagereturn=(Message) objectIntputStream.readObject();
				
				if(messagereturn.getMessageType().equals(MessageType.RegisterSuccess)) {
					JOptionPane.showMessageDialog(this, "注册成功");
					this.dispose();
				}
				
				
			} catch (UnknownHostException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		else if(arg0.getSource() == noButton) {
			this.dispose();
		}
	}
	
	//获取注册信息包
	public Message getRegiserMassage()
	{
		Message message = new Message();
		
		String userName = IDTextField.getText().trim();
		String password = new String(passwdField.getPassword());
		String rePassword = new String(repasswdField.getPassword());
		String phoneNum = phoneTextField.getText().trim();
		
		User user = new User();
		UserData userData = new UserData();
		if(password.equals(rePassword)) {
			
			userData.setPhoneNum(phoneNum);
			
			user.setUserName(userName);
			user.setPassword(password);			
			user.setUserData(userData);
			
			message.setUser(user);
			message.setMessageType(MessageType.Register);
			
		}else {
			JOptionPane.showMessageDialog(this, "密码不一致，请重新输入");
			passwdField.setText("");
			repasswdField.setText("");
		}
		return message;
	}
}
