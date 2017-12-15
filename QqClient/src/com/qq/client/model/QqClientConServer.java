/**
 * ���ǿͻ������ӷ������ĺ�̨
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
	
	//���͵�һ������
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
			//��֤�û���½
			if(message.getMessageType().equals(MessageType.LoginSuccess))
			{
				//����һ����QQ�źͷ������˱���ͨѶ���ӵ��߳�
				ClientConServerThread ccst=new ClientConServerThread(socket);
				//������ͨѶ�߳�
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
