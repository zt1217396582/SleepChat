/**
 * ������Ϣ��
 */

package com.qq.common;

import java.util.Date;

public class Message implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1770624737888028315L;

	private String messageType; //��Ϣ���ͣ��ж���Ϣ����ĸ������
	
	private String sender;//������
	
	private String getter;//������
	
	private String message;//��Ϣ����
	
	private Date sendTime;//����ʱ��
	
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
