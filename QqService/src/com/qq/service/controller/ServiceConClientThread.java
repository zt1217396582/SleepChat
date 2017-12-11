/**
 * 服务器和某个客户端的通讯线程
 */

package com.qq.service.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.qq.common.Message;
import com.qq.common.MessageType;

public class ServiceConClientThread extends Thread{

	Socket socket;
	
	public ServiceConClientThread(Socket socket)
	{
		//把服务器和该客户端的连接赋给socket
		this.socket = socket;
	}
	
	//通知其他用户
	public void otherNotify(String mine){
		//得到所有在线用户的线程
		HashMap hm =ManageClientThread.hashMap;
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			
			Message message=new Message();
			message.setMessage(mine);
			message.setMessageType(MessageType.ReturnOnlineFriend);
			//取出在线用户的ID号
			String onLineUserID=it.next().toString();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.geClientThread(onLineUserID).socket.getOutputStream());
				message.setGetter(onLineUserID);
				oos.writeObject(message);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		
		}
	}
	
	public void run()
	{
		boolean isStop = false;
		while(!isStop)
		{
			//这里该线程就可以接收客户端的信息
			ObjectInputStream objectInputStream;
			try {
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message message = (Message)objectInputStream.readObject();
				
				//System.out.println(message.getSender() + "给 " + message.getGetter() + "发送" + message.getMessage());
				//对从客户端取得的消息，进行类型判断
				if(message.getMessageType().equals(MessageType.CommonChat)){
				//完成转发
				//取得接收人的通信线程
				ServiceConClientThread serviceConClientThread = ManageClientThread.geClientThread(message.getGetter());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(serviceConClientThread.socket.getOutputStream());
				objectOutputStream.writeObject(message);
				}
				else if(message.getMessageType().equals(MessageType.GetOnlineFriend)){
					//把在服务器的好友给该客户端返回
					String res=ManageClientThread.getAllOnlineUserID();
					Message m=new Message();
					m.setMessageType(MessageType.ReturnOnlineFriend);
					m.setMessage(res);
					m.setGetter(message.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(m);
				}
				//对离线用户进行处理
				else if(message.getMessageType().equals(MessageType.UserOffLine)){
					HandleClientMessage handleClientMessage = new HandleClientMessage();
					handleClientMessage.deleteOffLineUser(message);
					
					Message m=new Message();
					m.setMessageType(MessageType.OffLineSuccess);
					ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(m);
					oos.close();
					//关闭连接
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
