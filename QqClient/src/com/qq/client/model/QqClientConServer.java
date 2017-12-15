/**
 * 这是客户端连接服务器的后台
 */

package com.qq.client.model;

import java.net.*;
import java.io.*;

import com.qq.client.controller.ClientConServerThread;
import com.qq.client.controller.ManageClientConServerThread;
import com.qq.common.Message;
import com.qq.common.MessageType;

public class QqClientConServer {
	
	public static Socket socket;
	
	//发送第一次请求
	public boolean SendLoginInfoToServer(Object object)
	{
		boolean b = false;
		try {
			//System.out.println("adsf");
			socket = new Socket("127.0.0.1", 9999);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(object);
			
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			
			Message message = (Message)objectInputStream.readObject();
			//验证用户登陆
			if(message.getMessageType().equals(MessageType.LoginSuccess))
			{
				//创建一个该QQ号和服务器端保持通讯连接的线程
				ClientConServerThread ccst=new ClientConServerThread(socket);
				//启动该通讯线程
				ccst.start();
				ManageClientConServerThread.addClientConServerThread(((Message)object).getUser().getUserId(), ccst);
				b = true;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return b;
	}
	
}
