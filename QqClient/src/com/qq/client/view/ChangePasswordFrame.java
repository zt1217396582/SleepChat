package com.qq.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class ChangePasswordFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8264690432156941782L;
	
	private JPanel contentPane;
	private JTextField IDTextField;
	private JPasswordField NowpasswdField;
	private JPasswordField NewpasswdField;
	private JPasswordField repasswdField;
	private JButton yesButton;
	private JButton noButton;
	

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new ChangePasswordFrame();
	}

	public ChangePasswordFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 293, 234);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel IDLabel = new JLabel("帐号：");
		IDLabel.setBounds(35, 13, 60, 18);
		contentPane.add(IDLabel);
		
		JLabel nowpasswdLabel = new JLabel("原密码：");
		nowpasswdLabel.setBounds(35, 44, 75, 18);
		contentPane.add(nowpasswdLabel);
		
		JLabel newPasswdLabel = new JLabel("新密码：");
		newPasswdLabel.setBounds(35, 74, 75, 18);
		contentPane.add(newPasswdLabel);
		
		JLabel rePasswdLabel = new JLabel("确认密码：");
		rePasswdLabel.setBounds(20, 104, 75, 18);
		contentPane.add(rePasswdLabel);
		
		IDTextField = new JTextField();
		IDTextField.setBounds(109, 10, 127, 24);
		contentPane.add(IDTextField);
		IDTextField.setColumns(10);
		
		NowpasswdField = new JPasswordField();
		NowpasswdField.setBounds(109, 41, 127, 24);
		contentPane.add(NowpasswdField);
		NowpasswdField.setColumns(10);
		
		NewpasswdField = new JPasswordField();
		NewpasswdField.setBounds(109, 72, 127, 24);
		contentPane.add(NewpasswdField);
		NewpasswdField.setColumns(10);
		
		repasswdField = new JPasswordField();
		repasswdField.setBounds(109, 103, 127, 24);
		contentPane.add(repasswdField);
		repasswdField.setColumns(10);
		
		yesButton = new JButton("确定");
		yesButton.setBounds(38, 147, 92, 27);
		contentPane.add(yesButton);
		yesButton.addActionListener(this);
		this.getRootPane().setDefaultButton(yesButton);
		
		noButton = new JButton("取消");
		noButton.setBounds(144, 147, 92, 27);
		contentPane.add(noButton);
		noButton.addActionListener(this);
		
		this.setTitle("修改密码");
		this.setVisible(true);
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==yesButton){
			
			Message message = getPasswordMessage();
			
			//建立连接，发送修改密码信息
			Socket socket;
			try {
				socket = new Socket("127.0.0.1", 9999);
				ObjectOutputStream objectOutputStream;
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(message);
				
				//等待数据库返回密码修改成功的信息，然后关闭界面，显示密码修改成功的对话框
				ObjectInputStream objectIntputStream;
				objectIntputStream = new ObjectInputStream(socket.getInputStream());
				Message messagereturn=(Message) objectIntputStream.readObject();
				
				if(messagereturn.getMessageType().equals(MessageType.ChangePasswordSuccess)) {
					JOptionPane.showMessageDialog(this, "密码修改成功！");
					this.dispose();
				}
				else if(messagereturn.getMessageType().equals(MessageType.ChangePasswordFail)){
					JOptionPane.showMessageDialog(this, "密码修改失败！");
				}
				
			} catch (UnknownHostException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==noButton){
			this.dispose();
		}
	}
	//获取修改密码信息包
		public Message getPasswordMessage()
		{
			Message message = new Message();
			
			String userName = IDTextField.getText().trim();
			String nowpassword = new String(NowpasswdField.getPassword());
			String newpassword=new String(NewpasswdField.getPassword());
			String rePassword = new String(repasswdField.getPassword());
			
			User user = new User();
			message.setMessageType(MessageType.ChangePassword);
			
			if(newpassword.equals(rePassword)) {
				user.setUserId(userName);
				user.setPassword(nowpassword);			
				message.setUser(user);
				message.setMessage(newpassword);
				
			}else {
				JOptionPane.showMessageDialog(this, "新密码不一致，请重新输入");
				NowpasswdField.setText("");
				NewpasswdField.setText("");
				repasswdField.setText("");
			}
			return message;
		}
}
