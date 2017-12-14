/**
 * 我的好友列表，也包括陌生人，黑名单
 */

package com.qq.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

import com.qq.client.controller.*;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class QqFriendList extends JFrame implements ActionListener, MouseListener{
	//处理第一张卡片(我的好友)
	
	JPanel jPanelFriend, jPanelFriend2, jPanelFriend3;
	JButton jPanelFriend_Button, jPanelFriend_Button2, jPanelFriend_Button3;
	JScrollPane jScrollPaneFriend;
	String ownerId;
	//处理第二张卡片(陌生人)
	
	JPanel jPanelStranger, jPanelStranger2, jPanelStranger3;
	JButton jPanelStranger_Button, jPanelStranger_Button2, jPanelStranger_Button3;
	JScrollPane jScrollPaneStranger;
	JLabel [] jLabels;
	//把整个JFrame设置成CardLayout
	CardLayout cardLayout;
	final PopupMenu menu;
	MenuItem item;
	
	String userId;
	
	Message message = new Message();
	//获取最后一次登录时间
	java.util.Date daTe=ClientLoginFrame.date;
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		QqFriendList qqFriendList = new QqFriendList("1");

	}
	
	//更新在线好友情况
	public void updateFriend(Message message){
		String onLineFriend[]=message.getMessage().split(" ");
		for(int i=0;i<onLineFriend.length;i++){
			jLabels[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);
		}
	}
	
	//好友离线判断
	public void offlineFriend(Message message){
		String offLineUserID=message.getMessage();
		System.out.println(offLineUserID);
		jLabels[Integer.parseInt(offLineUserID)-1].setEnabled(false);
		
	}

	public int userCount(Message message){
		//建立连接，发送请求好友数量信息
		Socket socket;
		int usercount = 50;
		try {
			message.setMessageType(MessageType.GetuserCount);
			socket = new Socket("127.0.0.1", 9999);
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(message);
			
			//等待数据库返回好友数量的信息
			ObjectInputStream objectIntputStream;
			objectIntputStream = new ObjectInputStream(socket.getInputStream());
			Message messagereturn=(Message) objectIntputStream.readObject();
			
			if(messagereturn.getMessageType().equals(MessageType.GetuserCountSuccess)) {
				usercount=Integer.parseInt(messagereturn.getMessage());
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
		return usercount;
	}

	//建立连接，发送用户下线消息包
	public void setOffLineUser(Message message){
		try {
			ObjectOutputStream objectOutputStream=new ObjectOutputStream
					(ManageClientConServerThread.getClientConServerThread
							(ClientLoginFrame.userID).getSocket().getOutputStream());
			message.setMessageType(MessageType.UserOffLine);
			message.setSendTime(daTe);
			message.setMessage(ClientLoginFrame.userID);
			objectOutputStream.writeObject(message);
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
	public QqFriendList(String ownerId)
	{
		this.ownerId = ownerId;
		//处理第一张卡片（显示好友列表）
		jPanelFriend_Button = new JButton("我的好友");
		jPanelFriend_Button2 = new JButton("陌生人");
		jPanelFriend_Button2.addActionListener(this);
		jPanelFriend_Button3 = new JButton("黑名单");
		
		jPanelFriend = new JPanel(new BorderLayout());
		//假定有50个好友
		jPanelFriend2 = new JPanel(new GridLayout(50,1,4,4));
		//给jPanelFriend2初始化50个好友
		jLabels = new JLabel[50];
		for(int i = 0; i < userCount(message); i++)
		{
			jLabels[i] = new JLabel( i+1+"", new ImageIcon("image/headphoto.gif"), JLabel.LEFT);
			jLabels[i].setEnabled(false);
			if(jLabels[i].getText().equals(ownerId)){
				jLabels[i].setEnabled(true);
			}
			jLabels[i].addMouseListener(this);
			jPanelFriend2.add(jLabels[i]);
		}

		jPanelFriend3 = new JPanel(new GridLayout(2,1));
		//把两个按钮加入到jPanelFriend3
		jPanelFriend3.add(jPanelFriend_Button2);		
		jPanelFriend3.add(jPanelFriend_Button3);
		
		jScrollPaneFriend = new JScrollPane(jPanelFriend2);
		
		//对jPanelFriend初始化
		jPanelFriend.add(jPanelFriend_Button, "North");
		jPanelFriend.add(jScrollPaneFriend, "Center");
		jPanelFriend.add(jPanelFriend3, "South");

		
		
		//处理第二张卡片
		jPanelStranger_Button = new JButton("我的好友");
		jPanelStranger_Button.addActionListener(this);
		jPanelStranger_Button2 = new JButton("陌生人");
		jPanelStranger_Button3 = new JButton("黑名单");
		
		jPanelStranger = new JPanel(new BorderLayout());
		//假定有20个陌生人
		jPanelStranger2 = new JPanel(new GridLayout(20,1,4,4));	
		
		//给jPanelStranger2初始化20个陌生人
		JLabel [] jLabels2 = new JLabel[20];
		
		for(int i = 0; i < jLabels2.length; i++)
		{
			jLabels2[i] = new JLabel( i+1+"", new ImageIcon("image/headphoto.gif"), JLabel.LEFT);
			jPanelStranger2.add(jLabels2[i]);
		}
		
		jPanelStranger3 = new JPanel(new GridLayout(2,1));
		//把两个按钮加入到jPanelFriend3
		jPanelStranger3.add(jPanelStranger_Button);		
		jPanelStranger3.add(jPanelStranger_Button2);
		
		jScrollPaneStranger = new JScrollPane(jPanelStranger2);
		
		//对jPanelStranger初始化
		jPanelStranger.add(jPanelStranger3, "North");
		jPanelStranger.add(jScrollPaneStranger, "Center");
		jPanelStranger.add(jPanelStranger_Button3, "South");
		
		
		cardLayout = new CardLayout();
		this.setLayout(cardLayout);
		this.add(jPanelFriend, "1");
		this.add(jPanelStranger, "2");
		//在窗口显示自己的编号
		this.setTitle(ownerId);	
		
		//右键菜单及监听
		menu=new PopupMenu();
		item = new MenuItem();
		item.setLabel("用户资料");
		menu.add(item);
		this.add(menu);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == item) {
					UserMessage userMessage= new UserMessage();
					
					Message message = new Message();
					User user = new User();
					user.setUserId(userId);
					message.setUser(user);
					message.setMessageType(MessageType.GetUserData);
					
					Message messageReturn = userMessage.waitServiceMessage(message);
					
					userMessage.setUserDataMessage(messageReturn);
					
					if(messageReturn.getUser().getUserId().equals(ClientLoginFrame.userID))
					{
						userMessage.setEdit(true);
					}else {
						userMessage.setEdit(false);
					}
					
				}
			}
		});
		
		
		//this.add(jPanelStranger, "Center");
		
		this.setSize(250,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//对关闭窗口做出响应
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			setOffLineUser(message);
			System.exit(0);
			}
		});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		//如果点击了陌生人按钮，就显示第二张卡片
		if(arg0.getSource() == jPanelFriend_Button2)
		{
			cardLayout.show(this.getContentPane(), "2");
		}else if(arg0.getSource() == jPanelStranger_Button)
		{
			cardLayout.show(this.getContentPane(), "1");
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		//响应用户双击的事件，并得到好友的编号
		if(arg0.getClickCount() == 2 && arg0.getButton() == MouseEvent.BUTTON1)
		{
			//得到该好友的编号
			String friendNo = ((JLabel)arg0.getSource()).getText();
			//System.out.println("正在与" + friendNo + "聊天");
			QqChat qqChat=new QqChat(this.ownerId, friendNo);
		
			//把聊天界面加入到管理类
			ManangeQqChat.addQqChat(this.ownerId+" "+friendNo, qqChat);
		
		}
		else if(arg0.getButton() == MouseEvent.BUTTON3)
		{
			menu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
			System.out.println("x:" + arg0.getX() + "y:" + arg0.getY());
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		JLabel j1 = (JLabel)arg0.getSource();
		j1.setForeground(Color.red);
		userId = j1.getText();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		JLabel j1 = (JLabel)arg0.getSource();
		j1.setForeground(Color.BLACK);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO 自动生成的方法存根
		
	}

}
