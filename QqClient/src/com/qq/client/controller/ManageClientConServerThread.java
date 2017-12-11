/**
 * 管理客户端与服务器保持通讯的线程类
 * */

package com.qq.client.controller;
import java.util.*;
public class ManageClientConServerThread {
	private static HashMap hm=new HashMap<String,ClientConServerThread>();
	
	//把创建好的线程ClientConServerThread，放入到hm
	public static void addClientConServerThread(String userId,ClientConServerThread ccst){
		hm.put(userId, ccst);
	}
	//可以通过QQID取得该线程
	public static ClientConServerThread getClientConServerThread(String userId){
		return (ClientConServerThread)hm.get(userId);
	}
}
