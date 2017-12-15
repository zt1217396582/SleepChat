/**
 * 管理好友等等
 * 
 * */

package com.qq.client.controller;

import java.util.*;

import com.qq.client.view.QqFriendList;

public class ManageQqFriendList {
	private static HashMap<String, QqFriendList> hm=new HashMap<String,QqFriendList>();

	public static void addQqFriendList(String qqID,QqFriendList qqFriendList){
		hm.put(qqID, qqFriendList);
	}
	public static QqFriendList getQqFriendList(String qqID){
		return (QqFriendList) hm.get(qqID);
	}

	public static void removeQqFriendList(String qqID){
		hm.remove(qqID);
	}

}
