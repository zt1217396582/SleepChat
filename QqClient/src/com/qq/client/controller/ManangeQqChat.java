/**
 *�����û��������
 * */


package com.qq.client.controller;

import java.util.*;

import com.qq.client.view.QqChat;
public class ManangeQqChat {
	private static HashMap<String, QqChat> hm=new HashMap<String,QqChat>();
	//����
	public static void addQqChat(String loginIDandFriendID,QqChat qqchat){
		hm.put(loginIDandFriendID, qqchat);
	}
	
	
	//ȡ��
	public static QqChat getQqChat(String loginIDandFriendID){
		return (QqChat) hm.get(loginIDandFriendID);
	}
}
