/**
 * �ͻ��˺ͷ������˱���ͨѶ���߳�
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
			//��ͣ��ȡ�ӷ���˷�������Ϣ
			try {
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				Message message=(Message) ois.readObject();
				//System.out.println("��ȡ���ӷ�������������Ϣ"+message.getSender()+"��"+message.getGetter()+"����"+message.getMessage());
				System.out.println(message.getMessageType());
				if(message.getMessageType().equals(MessageType.CommonChat)){
				
			//�Ѵӷ�������õ���Ϣ����ʾ������ʾ���������
				QqChat qqChat=ManangeQqChat.getQqChat(message.getGetter()+" "+message.getSender());
			//��ʾ
				qqChat.showMessage(message);
				}
				else if(message.getMessageType().equals(MessageType.ReturnOnlineFriend)){
					String getter=message.getGetter();
					//�޸���Ӧ�ĺ����б�
					qqFriendList=ManageQqFriendList.getQqFriendList(getter);
				
					//�������ߺ���
					if(qqFriendList!=null){
					qqFriendList.updateFriend(message);
					}
				}
				else if(message.getMessageType().equals(MessageType.NotifyOfflineUser))
				{
					String getter=message.getGetter();
					//�޸���Ӧ�ĺ����б�
					qqFriendList=ManageQqFriendList.getQqFriendList(getter);
					if(qqFriendList!=null){
						qqFriendList.offlineFriend(message);
					}
				}
				else if(message.getMessageType().equals(MessageType.OffLineSuccess))
				{
					//�����ߵ��û��Ƴ������б����
					ManageQqFriendList.removeQqFriendList(message.getMessage());
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
