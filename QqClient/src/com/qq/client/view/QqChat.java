/**
 * 与好友聊天的界面
 * 因为客户端要处于读取的状态因此我们把它做成一个线程
 */

package com.qq.client.view;

import javax.swing.*;

import com.qq.client.controller.ManageClientConServerThread;
import com.qq.common.Message;
import com.qq.common.MessageType;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QqChat extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2870356635447654905L;
	
	JTextArea jTextArea;
	JTextField jTextField;
	JButton jButton;
	JPanel jPanel;
	String ownerId;
	String friendId;
	JScrollPane jscrollPane;
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//QqChat qqChat = new QqChat("1", "2");
		
	}
	
	public QqChat(String ownerId, String friend)
	{
		this.ownerId = ownerId;
		this.friendId = friend;
		jTextArea = new JTextArea();
		jTextArea.setEditable(false);// 设置为无法编辑聊天窗口的信息
		jTextArea.setLineWrap(true);// 激活自动换行功能  
		jTextArea.setWrapStyleWord(true);// 激活断行不断字功能 
		jscrollPane = new JScrollPane(jTextArea);// 给文本框添加滚动条功能  
		
		jTextField = new JTextField(15);
		jButton = new JButton("发送");
		// 设置发送为默认按钮
		this.getRootPane().setDefaultButton(jButton);
		jButton.addActionListener(this);
		jPanel = new JPanel();
		jPanel.add(jTextField);
		jPanel.add(jButton);
		
		this.add(jscrollPane,"Center");
		this.add(jPanel, "South");
		this.setTitle(ownerId + " 正在和 " + friend + " 聊天");
		this.setIconImage((new ImageIcon("image/headphoto.gif").getImage()));
		this.setSize(500,400);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		
	}

	//显示消息
	public void showMessage(Message message){
		//若不为空消息则显示到文本框
		if(!(message.getMessage().equals(null) || message.getMessage().equals(""))) {
			System.out.println("message:" + message.getMessage());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd E kk:mm:ss");
			String date = dateFormat.format(message.getSendTime());
			
			String info= date + ":\n" + 
					message.getSender()+" 对 "+message.getGetter()+" 说："+message.getMessage()+"\r\n\n";
			this.jTextArea.append(info);
		}
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
		//如果用户点击了发送按钮,将message的内容打包发送到服务端
		if(arg0.getSource() == jButton)
		{
			Message message = new Message();
			message.setMessageType(MessageType.CommonChat);
			message.setGetter(this.friendId);
			message.setSender(this.ownerId);
			message.setMessage(jTextField.getText());
			message.setSendTime(new Date());
			this.showMessage(message);
			
			this.jTextField.setText("");
			
			//发送给服务器 
			ObjectOutputStream objectOutputStream;
			try {
				objectOutputStream = new ObjectOutputStream
						(ManageClientConServerThread.getClientConServerThread(ownerId).getSocket().getOutputStream());
				objectOutputStream.writeObject(message);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

}
