/**
 * ����ͻ��������������ͨѶ���߳���
 * */

package com.qq.client.controller;
import java.util.*;
public class ManageClientConServerThread {
	private static HashMap hm=new HashMap<String,ClientConServerThread>();
	
	//�Ѵ����õ��߳�ClientConServerThread�����뵽hm
	public static void addClientConServerThread(String userId,ClientConServerThread ccst){
		hm.put(userId, ccst);
	}
	//����ͨ��QQIDȡ�ø��߳�
	public static ClientConServerThread getClientConServerThread(String userId){
		return (ClientConServerThread)hm.get(userId);
	}
}
