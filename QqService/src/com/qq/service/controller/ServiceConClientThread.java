/**
 * ��������ĳ���ͻ��˵�ͨѶ�߳�
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
		//�ѷ������͸ÿͻ��˵����Ӹ���socket
		this.socket = socket;
	}
	
	//֪ͨ�����û�
	public void otherNotify(String mine){
		//�õ����������û����߳�
		HashMap hm =ManageClientThread.hashMap;
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			
			Message message=new Message();
			message.setMessage(mine);
			message.setMessageType(MessageType.ReturnOnlineFriend);
			//ȡ�������û���ID��
			String onLineUserID=it.next().toString();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.geClientThread(onLineUserID).socket.getOutputStream());
				message.setGetter(onLineUserID);
				oos.writeObject(message);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		
		}
	}
	
	public void run()
	{
		boolean isStop = false;
		while(!isStop)
		{
			//������߳̾Ϳ��Խ��տͻ��˵���Ϣ
			ObjectInputStream objectInputStream;
			try {
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message message = (Message)objectInputStream.readObject();
				
				//System.out.println(message.getSender() + "�� " + message.getGetter() + "����" + message.getMessage());
				//�Դӿͻ���ȡ�õ���Ϣ�����������ж�
				if(message.getMessageType().equals(MessageType.CommonChat)){
				//���ת��
				//ȡ�ý����˵�ͨ���߳�
				ServiceConClientThread serviceConClientThread = ManageClientThread.geClientThread(message.getGetter());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(serviceConClientThread.socket.getOutputStream());
				objectOutputStream.writeObject(message);
				}
				else if(message.getMessageType().equals(MessageType.GetOnlineFriend)){
					//���ڷ������ĺ��Ѹ��ÿͻ��˷���
					String res=ManageClientThread.getAllOnlineUserID();
					Message m=new Message();
					m.setMessageType(MessageType.ReturnOnlineFriend);
					m.setMessage(res);
					m.setGetter(message.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(m);
				}
				//�������û����д���
				else if(message.getMessageType().equals(MessageType.UserOffLine)){
					HandleClientMessage handleClientMessage = new HandleClientMessage();
					handleClientMessage.deleteOffLineUser(message);
					
					Message m=new Message();
					m.setMessageType(MessageType.OffLineSuccess);
					ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(m);
					oos.close();
					//�ر�����
					socket.close();
					isStop = true;
				}
				
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
			
		}
	}
	
}
