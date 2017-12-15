/**
 * 客户端和服务器端保持通讯的线程
 * */

package com.qq.client.controller;

import java.io.*;
import java.net.*;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;


public class ClientConServerThread extends Thread {
	private Socket socket;
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ClientConServerThread(Socket socket) {
		this.socket=socket;
	}
	
	public void run(){
		boolean isStop = false;
		QqFriendList qqFriendList = null;
		while(!isStop){
			//不停读取从服务端发来的消息
			try {
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				Message message=(Message) ois.readObject();
				//System.out.println("读取到从服务器发来的消息"+message.getSender()+"给"+message.getGetter()+"内容"+message.getMessage());
				System.out.println(message.getMessageType());
				if(message.getMessageType().equals(MessageType.CommonChat)){
				
			//把从服务器获得的消息，显示到该显示的聊天界面
				QqChat qqChat=ManangeQqChat.getQqChat(message.getGetter()+" "+message.getSender());
			//显示
				qqChat.showMessage(message);
				}
				else if(message.getMessageType().equals(MessageType.ReturnOnlineFriend)){
					String getter=message.getGetter();
					//修改相应的好友列表
					qqFriendList=ManageQqFriendList.getQqFriendList(getter);
				
					//更新在线好友
					if(qqFriendList!=null){
					qqFriendList.updateFriend(message);
					}
				}
				else if(message.getMessageType().equals(MessageType.NotifyOfflineUser))
				{
					String getter=message.getGetter();
					//修改相应的好友列表
					qqFriendList=ManageQqFriendList.getQqFriendList(getter);
					if(qqFriendList!=null){
						qqFriendList.offlineFriend(message);
					}
				}
				else if(message.getMessageType().equals(MessageType.OffLineSuccess))
				{
					//将下线的用户移出好友列表管理
					ManageQqFriendList.removeQqFriendList(message.getMessage());
					socket.close();
					isStop = true;
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
