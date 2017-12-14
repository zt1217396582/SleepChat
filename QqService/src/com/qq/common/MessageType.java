/**
 * 定义包的种类
 * */

package com.qq.common;

public interface MessageType 
{
	String LoginRequest = "1.0";//登录请求包	
	String LoginSuccess="1.1";//表示登陆成功	
	String LoginFail="1.2";//表示登陆失败
	
	String CommonChat="2.0";//普通聊天信息包
	
	String GetOnlineFriend="3";//要求在线好友的包	
	String ReturnOnlineFriend="3.1";//返回在线好友
	String GetuserCount="3.2";//请求用户数量
	String GetuserCountSuccess="3.3";//请求用户数量成功
	
	String UserData = "4.0";//用户资料信息包	
	String UserDataUpdateSuccess = "4.1";//用户资料更新成功
	String UserDataUpdateFail = "4.2";//用户资料更新失败
	String GetUserData = "4.3";//获取用户资料
	String SetUserDataRequest="4.4";//用户设置个人资料请求
	String SetUserDataAllow="4.5";//允许用户设置个人资料
	
	String Register = "5.0";//用户注册信息包
	String RegisterSuccess = "5.1";//用户注册成功
	String RegisterFail = "5.2";//用户注册失败
	
	String UserOnline="6.0";//用户上线消息包
	String UserOffLine="6.1";//用户下线消息包
	String OffLineSuccess="6.2";//用户成功下线
	String NotifyOfflineUser="6.3";//通知其他用户有人下线
	
	String GetLastLoginTime="7.0";//获取最后登录时间
	
	String ChangePassword="8.0";//用户修改密码信息包
	String ChangePasswordSuccess="8.1";//用户修改密码成功
	String ChangePasswordFail="8.2";//用户修改密码失败
}
