package com.qq.service.controller;

import java.util.*;

public class ManageClientThread {

	public static HashMap<String, ServiceConClientThread> hashMap = new HashMap<String,ServiceConClientThread>();
	//��hashmap�����һ���ͻ���ͨѶ�߳�
	public static void addClientThread(String userId, ServiceConClientThread serviceConClientThread)
	{
		hashMap.put(userId, serviceConClientThread);
	}
	
	public static ServiceConClientThread geClientThread(String userId)
	{
		return (ServiceConClientThread)hashMap.get(userId);
	}
	//�Ƴ��߳�
	public static void removeClientThread(String userId)
	{
		hashMap.remove(userId);
	}
	
	//���ص�ǰ�����û�����Ϣ
	public static String getAllOnlineUserID(){
		//ʹ�õ��������
		Iterator<String> it=hashMap.keySet().iterator();
		String res="";
		while(it.hasNext()){
			res+=it.next().toString()+" ";
		}
		return res;
	}
}
