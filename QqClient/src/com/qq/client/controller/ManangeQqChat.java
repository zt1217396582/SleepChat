/**
 *管理用户聊天界面
 * */


package com.qq.client.controller;

import java.util.*;

import com.qq.client.view.QqChat;
public class ManangeQqChat {
	private static HashMap<String, QqChat> hm=new HashMap<String,QqChat>();
	//加入
	public static void addQqChat(String loginIDandFriendID,QqChat qqchat){
		hm.put(loginIDandFriendID, qqchat);
	}
	
	
	//取出
	public static QqChat getQqChat(String loginIDandFriendID){
		return (QqChat) hm.get(loginIDandFriendID);
	}
}
