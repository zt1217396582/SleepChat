/**
 * 新建一个线程用于处理服务器循环监听客户端的而产生的阻塞问题
 */

package com.qq.service.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.service.db.ServiceConDatabase;
import com.qq.service.db.SqlCommandList;
import com.qq.service.db.SqlHelper;

public class ServiceThread extends Thread{
	
	Socket socket;
	ServerSocket serverSocket;
	HandleClientMessage handleClientMessage;
	public void run()
	{
		try {
			handleClientMessage=new HandleClientMessage();
			//监听9999
			System.out.println("正在监听9999端口");
			//连接读取数据库信息
			ServiceConDatabase.getConnection();
			serverSocket = new ServerSocket(9999);
			//阻塞，等待连接
			while(true)
			{
				socket = serverSocket.accept();
				
				//接收客户端发来的信息
				
				//读取注册信息
				Message message = getMessage(socket);
				Message messageReturn = new Message();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				//对注册信息包进行处理
				if(message.getMessageType().equals(MessageType.Register))
				{
					//将下一个可用userId添加到message中
					handleClientMessage.setUserIdToMessage(message);
					
					//添加帐号到数据库,在全部用户，用户资料，在线用户中各创建一条新的数据
					boolean isRegisterSuccess = handleClientMessage.registerMessaage(message);
					//返回一条注册成功的信息，让客户端做出响应
					if(isRegisterSuccess)
					{
						messageReturn.setMessageType(MessageType.RegisterSuccess);
						handleClientMessage.judgeUserOnOffLine(message);
						objectOutputStream.writeObject(messageReturn);
					}else{
						messageReturn.setMessageType(MessageType.RegisterFail);
						objectOutputStream.writeObject(messageReturn);
						//关闭连接
						socket.close();
					}
					
				}
				
				//对用户修改密码包进行处理
				else if(message.getMessageType().equals(MessageType.ChangePassword)){
					String passwd = message.getUser().getPassword();	
					//如果原密码正确，返回修改密码信息
					if(message.getUser().getPassword().equals(passwd)){
						boolean cup=handleClientMessage.changeUserPassword(message);
						if(cup){
							messageReturn.setMessageType(MessageType.ChangePasswordSuccess);
							objectOutputStream.writeObject(messageReturn);
						}
						else{
							messageReturn.setMessageType(MessageType.ChangePasswordFail);
							objectOutputStream.writeObject(messageReturn);
						}
						//关闭连接
						socket.close();
					}
				}
				//对登录请求包进行处理
				else if(message.getMessageType().equals(MessageType.LoginRequest))
				{
					String passwd = getPasswd(message);
					
					//如果密码正确，返回给客户端成功登录的信息包
					if(message.getUser().getPassword().equals(passwd))
					{				
						messageReturn.setMessageType(MessageType.LoginSuccess);
						objectOutputStream.writeObject(messageReturn);
						
						//单开一个线程，让该线程与该客户端保持通讯
						ServiceConClientThread serviceConClientThread = new ServiceConClientThread(socket);
						//添加一个客户端的线程
						ManageClientThread.addClientThread(message.getUser().getUserId(), serviceConClientThread);
						//启动与该客户端通讯的线程
						serviceConClientThread.start();
						
						//通知其他在线用户
						serviceConClientThread.onlineOtherNotify(message.getUser().getUserId());
						
					}else{
						messageReturn.setMessageType(MessageType.LoginFail);
						objectOutputStream.writeObject(messageReturn);
						//关闭连接
						socket.close();
					}
				}
				//对用户资料包进行处理
				else if(message.getMessageType().equals(MessageType.UserData))
				{
					//更新数据库的用户资料
					handleClientMessage.upDateUserData(message);
					System.out.println("用户资料成功传入");
					
					//返回更新成功的信息
					
					messageReturn.setMessageType(MessageType.UserDataUpdateSuccess);
					objectOutputStream.writeObject(messageReturn);
					//关闭连接
					socket.close();
				}
				//对获取个人资料包进行处理
				else if(message.getMessageType().equals(MessageType.GetUserData))
				{
					messageReturn = handleClientMessage.searchUserData(message);
					objectOutputStream.writeObject(messageReturn);
					//关闭连接
					socket.close();
					
				}
				//对好友列表数量进行处理
				else if(message.getMessageType().equals(MessageType.GetuserCount)){
					messageReturn=handleClientMessage.searchUserCount(message);
					objectOutputStream.writeObject(messageReturn);
					//关闭连接
					socket.close();
				}
				//对在线用户进行处理
				else if(message.getMessageType().equals(MessageType.UserOnline)){
					messageReturn=handleClientMessage.getLastOnlineTime(message);
					objectOutputStream.writeObject(messageReturn);
					
					handleClientMessage.judgeUserOnOffLine(message);
					//关闭连接
					socket.close();
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Message getMessage(Socket socket)
	{
		Message message = new Message();
		
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			message = (Message)objectInputStream.readObject();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return message;
	}
	
	public String getPasswd(Message message)
	{
		String passwd = null;
		SqlHelper sqlHelper = new SqlHelper();
		String [] parameters = {};
		ResultSet resultSet = sqlHelper.searchSqlData(SqlCommandList.searchAllUser, parameters);
		try {
			int id;
			String strId;
			while(resultSet.next()) { 
				id = ((int)resultSet.getObject(1));
				strId = Integer.toString(id);
				if(strId.equals(message.getUser().getUserId()))
				{
					passwd = (String) resultSet.getObject(3);
					break;
				}
			}
			
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return passwd;
		
	}

}
