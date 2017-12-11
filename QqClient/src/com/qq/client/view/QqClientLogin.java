/**
 * qq客户端登录界面
 */

package com.qq.client.view;

import javax.swing.*;

import com.qq.client.controller.*;
import com.qq.client.model.*;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

public class QqClientLogin extends JFrame implements ActionListener{

	//定义北部需要的组件
	JLabel jLabel;
	
	//定义中部需要的组件
	//中部有三个JPanel，由一个选项卡窗口管理
	JTabbedPane jTabbedPane;
	JPanel jPanel2, jPanel3, jPanel4;
	JLabel jPanel2_Label, jPanel2_Label2, jPanel2_Label3, jPanel2_Label4;
	JButton jPanel2_Button;
	JTextField jPanel2_TextField;
	JPasswordField jPanel2_PasswordField;
	JCheckBox jPanel2_CheckBox, jPanel2_CheckBox2;
	
	//定义南部需要的组件
	JPanel jPanel;
	JButton jPanel_Button, jPanel_Button2, jPanel_Button3;
	
	User user = new User();
	static String userID;
	static java.util.Date date=null;
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		QqClientLogin qqClientLogin = new QqClientLogin();
	}
	
	public QqClientLogin()
	{
		//处理北部
		jLabel = new JLabel(new ImageIcon("image/logo.gif"));
		
		//处理中部
		jPanel2 = new JPanel(new GridLayout(3,3));
		jPanel2_Label = new JLabel("QQ号码", JLabel.CENTER);
		jPanel2_Label2 = new JLabel("QQ密码", JLabel.CENTER);
		jPanel2_Label3 = new JLabel("忘记密码", JLabel.CENTER);
		jPanel2_Label3.setForeground(Color.blue);
		jPanel2_Label4 = new JLabel("申请密码保护", JLabel.CENTER);
		jPanel2_Label4.setForeground(Color.blue);
		jPanel2_Button = new JButton("修改号码");
		jPanel2_TextField = new JTextField();
		jPanel2_PasswordField = new JPasswordField();
		jPanel2_CheckBox = new JCheckBox("隐身登录");
		jPanel2_CheckBox2 = new JCheckBox("记住密码");
		
		//把控件加入到jPanel2
		jPanel2.add(jPanel2_Label);
		jPanel2.add(jPanel2_TextField);
		jPanel2.add(jPanel2_Button);
		jPanel2.add(jPanel2_Label2);
		jPanel2.add(jPanel2_PasswordField);
		jPanel2.add(jPanel2_Label3);
		jPanel2.add(jPanel2_CheckBox);
		jPanel2.add(jPanel2_CheckBox2);
		jPanel2.add(jPanel2_Label4);
		//创建选项卡窗口
	/*	jTabbedPane = new JTabbedPane();
		jTabbedPane.add("QQ号码",jPanel2);
		jPanel3 = new JPanel();
		jTabbedPane.add("手机号码",jPanel3);
		jPanel4 = new JPanel();
		jTabbedPane.add("电子邮件",jPanel4);*/
		
		//处理南部
		jPanel = new JPanel();
		jPanel_Button = new JButton("登录");
		// 设置登录为默认按钮
		this.getRootPane().setDefaultButton(jPanel_Button);
		
		//响应用户点击登录
		jPanel_Button.addActionListener(this);
		
		jPanel_Button2 = new JButton("注册");
		
		//响应用户点击注册
		jPanel_Button2.addActionListener(this);
		
		//jPanel_Button3 = new JButton("注册向导");
		
		//把三个按钮放入jPanel
		jPanel.add(jPanel_Button);
		jPanel.add(jPanel_Button2);
		//jPanel.add(jPanel_Button3);
		
		this.add(jLabel, "North");
		this.add(jPanel2, "Center");
		this.add(jPanel,"South");
		this.setSize(350,240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("寐语社");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
	}

	//设置在线状态
	public Message setOnlineUser(Message message){
		//建立连接，发送在线用户消息包
		Socket socket;
		Message messagereturn = null;
		try {
			message.setMessageType(MessageType.UserOnline);
			socket = new Socket("127.0.0.1", 9999);
			ObjectOutputStream objectOutputStream;
			message.setUser(user);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(message);
			
			//等待服务器返回message
			ObjectInputStream objectIntputStream;
			objectIntputStream = new ObjectInputStream(socket.getInputStream());
			messagereturn=(Message) objectIntputStream.readObject();
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return messagereturn;
	}
	
	//初始化最后登录时间，以及帐号
	public void getLastLoginTimeAndUser(Message messagereturn){
		date=messagereturn.getSendTime();
		userID=messagereturn.getMessage();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
		//如果用户点击登录
		if(arg0.getSource() == jPanel_Button)
		{
			System.out.println("正在验证。。");
			QqClientUser qqClientUser = new QqClientUser();
			//User user = new User();
			user.setUserId(jPanel2_TextField.getText().trim());
			user.setPassword(new String(jPanel2_PasswordField.getPassword()));
			System.out.println("用户发送的id：" + user.getUserId() + "密码：" + user.getPassword());
			Message message = new Message();
			message.setUser(user);
			message.setMessageType(MessageType.LoginRequest);
			if(qqClientUser.checkUser(message))
			{
				//设置在线用户状态,并初始化最后登录时间以及帐号
				getLastLoginTimeAndUser(setOnlineUser(message));
				
				
				try {	
					
				//创建好友列表
					QqFriendList qqList=new QqFriendList(user.getUserId());
					ManageQqFriendList.addQqFriendList(user.getUserId(), qqList);
					
				//发送一个要求返回在线好友的请求	
					ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(user.getUserId()).getSocket().getOutputStream());
				
				// 声明一个Message，用来发送信息
					Message messageSend=new Message();
					messageSend.setMessageType(MessageType.GetOnlineFriend);
					
					//指明登陆账号的好友情况
					messageSend.setSender(user.getUserId());
					oos.writeObject(messageSend);
					
					
				
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				//关闭登录界面
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(this, "用户密码错误");
			}
		}
		//如果用户点击注册
		else if(arg0.getSource() == jPanel_Button2)
		{
			new RegisterFrame();
		}
	}

}
