package com.qq.service.controller;

import java.util.*;

public class ManageClientThread {

	public static HashMap<String, ServiceConClientThread> hashMap = new HashMap<String,ServiceConClientThread>();
	//向hashmap中添加一个客户端通讯线程
	public static void addClientThread(String userId, ServiceConClientThread serviceConClientThread)
	{
		hashMap.put(userId, serviceConClientThread);
	}
	
	public static ServiceConClientThread geClientThread(String userId)
	{
		return (ServiceConClientThread)hashMap.get(userId);
	}
	//移除线程
	public static void removeClientThread(String userId)
	{
		hashMap.remove(userId);
	}
	
	//返回当前在线用户的信息
	public static String getAllOnlineUserID(){
		//使用迭代器完成
		Iterator<String> it=hashMap.keySet().iterator();
		String res="";
		while(it.hasNext()){
			res+=it.next().toString()+" ";
		}
		return res;
	}
}
