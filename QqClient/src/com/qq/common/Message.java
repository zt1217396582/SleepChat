/**
 * 聊天信息包
 */

package com.qq.common;

import java.util.Date;

public class Message implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1770624737888028315L;

	private String messageType; //信息类型，判断信息传输的各种情况
	
	private String sender;//发送者
	
	private String getter;//接收者
	
	private String message;//信息本身
	
	private Date sendTime;//发送时间
	
	private User user;
	
	public String getMessageType(){
		return messageType;
	}
	public void setMessageType(String messageType){
		this.messageType = messageType;
	}
	
	public String getSender(){
		return sender;
	}
	public void setSender(String sender){
		this.sender = sender;
	}
	
	public String getGetter(){
		return getter;
	}
	public void setGetter(String getter){
		this.getter = getter;
	}
	
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	
	public Date getSendTime(){
		return sendTime;
	}
	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
}
