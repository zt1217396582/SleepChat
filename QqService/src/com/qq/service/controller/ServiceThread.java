/**
 * �½�һ���߳����ڴ��������ѭ�������ͻ��˵Ķ���������������
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
			//����9999
			System.out.println("���ڼ���9999�˿�");
			//���Ӷ�ȡ���ݿ���Ϣ
			ServiceConDatabase.getConnection();
			serverSocket = new ServerSocket(9999);
			//�������ȴ�����
			while(true)
			{
				socket = serverSocket.accept();
				
				//���տͻ��˷�������Ϣ
				
				//��ȡע����Ϣ
				Message message = getMessage(socket);
				Message messageReturn = new Message();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				//��ע����Ϣ�����д���
				if(message.getMessageType().equals(MessageType.Register))
				{
					//����һ������userId��ӵ�message��
					handleClientMessage.setUserIdToMessage(message);
					
					//����ʺŵ����ݿ�,��ȫ���û����û����ϣ������û��и�����һ���µ�����
					boolean isRegisterSuccess = handleClientMessage.registerMessaage(message);
					//����һ��ע��ɹ�����Ϣ���ÿͻ���������Ӧ
					if(isRegisterSuccess)
					{
						messageReturn.setMessageType(MessageType.RegisterSuccess);
						handleClientMessage.judgeUserOnOffLine(message);
						objectOutputStream.writeObject(messageReturn);
					}else{
						messageReturn.setMessageType(MessageType.RegisterFail);
						objectOutputStream.writeObject(messageReturn);
						//�ر�����
						socket.close();
					}
					
				}
				
				//���û��޸���������д���
				else if(message.getMessageType().equals(MessageType.ChangePassword)){
					String passwd = message.getUser().getPassword();	
					//���ԭ������ȷ�������޸�������Ϣ
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
						//�ر�����
						socket.close();
					}
				}
				//�Ե�¼��������д���
				else if(message.getMessageType().equals(MessageType.LoginRequest))
				{
					String passwd = getPasswd(message);
					
					//���������ȷ�����ظ��ͻ��˳ɹ���¼����Ϣ��
					if(message.getUser().getPassword().equals(passwd))
					{				
						messageReturn.setMessageType(MessageType.LoginSuccess);
						objectOutputStream.writeObject(messageReturn);
						
						//����һ���̣߳��ø��߳���ÿͻ��˱���ͨѶ
						ServiceConClientThread serviceConClientThread = new ServiceConClientThread(socket);
						//���һ���ͻ��˵��߳�
						ManageClientThread.addClientThread(message.getUser().getUserId(), serviceConClientThread);
						//������ÿͻ���ͨѶ���߳�
						serviceConClientThread.start();
						
						//֪ͨ���������û�
						serviceConClientThread.onlineOtherNotify(message.getUser().getUserId());
						
					}else{
						messageReturn.setMessageType(MessageType.LoginFail);
						objectOutputStream.writeObject(messageReturn);
						//�ر�����
						socket.close();
					}
				}
				//���û����ϰ����д���
				else if(message.getMessageType().equals(MessageType.UserData))
				{
					//�������ݿ���û�����
					handleClientMessage.upDateUserData(message);
					System.out.println("�û����ϳɹ�����");
					
					//���ظ��³ɹ�����Ϣ
					
					messageReturn.setMessageType(MessageType.UserDataUpdateSuccess);
					objectOutputStream.writeObject(messageReturn);
					//�ر�����
					socket.close();
				}
				//�Ի�ȡ�������ϰ����д���
				else if(message.getMessageType().equals(MessageType.GetUserData))
				{
					messageReturn = handleClientMessage.searchUserData(message);
					objectOutputStream.writeObject(messageReturn);
					//�ر�����
					socket.close();
					
				}
				//�Ժ����б��������д���
				else if(message.getMessageType().equals(MessageType.GetuserCount)){
					messageReturn=handleClientMessage.searchUserCount(message);
					objectOutputStream.writeObject(messageReturn);
					//�ر�����
					socket.close();
				}
				//�������û����д���
				else if(message.getMessageType().equals(MessageType.UserOnline)){
					messageReturn=handleClientMessage.getLastOnlineTime(message);
					objectOutputStream.writeObject(messageReturn);
					
					handleClientMessage.judgeUserOnOffLine(message);
					//�ر�����
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return passwd;
		
	}

}
