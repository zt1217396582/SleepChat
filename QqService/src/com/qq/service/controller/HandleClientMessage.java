package com.qq.service.controller;

import java.security.MessageDigestSpi;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.attribute.standard.RequestingUserName;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;
import com.qq.common.UserData;
import com.qq.service.db.SqlCommandList;
import com.qq.service.db.SqlHelper;
import com.qq.service.view.ManagerFrame;

public class HandleClientMessage {
	
	SqlHelper sqlHelper = new SqlHelper();
	
	public HandleClientMessage(){};
	//��ע����Ϣ��ӵ����ݿ⣬�����Ƿ�ɹ�
	public boolean registerMessaage(Message message){
		boolean isCreateSqlalluser=false;
		boolean isCreateSqlUserData=false;
		
		String sqlalluser="insert into ȫ���û�(�ʺ�,�û���,����) values(";
		sqlalluser=sqlalluser+message.getUser().getUserId()+","+"'"+message.getUser().getUserName()+"'"+","+"'"+message.getUser().getPassword()+"'"+")";
		System.out.println(sqlalluser);
		
		String sqlUserData="insert into �û�����(�ʺ�,�û���,�ֻ���) values(";
		sqlUserData=sqlUserData+message.getUser().getUserId()+","+"'"+message.getUser().getUserName()+"'"+","+"'"+message.getUser().getUserData().getPhoneNum()+"'"+")";
		System.out.println(sqlUserData);

		//���ݿ�AllUser����
		sqlHelper.excuteData(sqlalluser);
		//��������
		String [] newUser = {message.getUser().getUserId(), message.getUser().getUserName(), message.getUser().getPassword()};
		TableManager.allUser.tableModel.addRow(newUser);
		isCreateSqlalluser = true;
		
		
		//���ݿ�UserData����
		sqlHelper.excuteData(sqlUserData);
		//��������
		String [] newUserData = {message.getUser().getUserId(), message.getUser().getUserName(), message.getUser().getUserData().getPhoneNum()};
		TableManager.UserData.tableModel.addRow(newUserData);
		isCreateSqlUserData = true;
		
		if(isCreateSqlalluser && isCreateSqlUserData) 
			return true;
		
		return false;
	}
	
	//�������ݿ��еĸ�������
	public void upDateUserData(Message message){
		User user = new User();
		UserData userData = new UserData();
		
		user = message.getUser();
		userData = message.getUser().getUserData();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(userData.getBirthday());
		
		String userId = user.getUserId();
		String [] userName = {user.getUserName(), userId};
		String [] sex = {userData.getSex(), userId};
		String [] age = {Integer.toString(userData.getAge()), userId};
		String [] phoneNum = {userData.getPhoneNum(), userId};
		String [] email = {userData.getEmail(), userId};
		String [] birthday = {date, userId};
		String [] blood = {userData.getBlood(), userId};
		String [] sign = {userData.getSign(), userId};
		
		//�������ݿ���û�����
		sqlHelper.updateData(SqlCommandList.updateUserDataUserName, userName);
		sqlHelper.updateData(SqlCommandList.updateUserDataSex, sex);
		sqlHelper.updateData(SqlCommandList.updateUserDataAge, age);
		sqlHelper.updateData(SqlCommandList.updateUserDataPhoneNum, phoneNum);
		sqlHelper.updateData(SqlCommandList.updateUserDataEmail, email);
		sqlHelper.updateData(SqlCommandList.updateUserDataBirthday, birthday);
		sqlHelper.updateData(SqlCommandList.updateUserDataBlood, blood);
		sqlHelper.updateData(SqlCommandList.updateUserDataSign, sign);
		
		//�����û����ϱ��е���Ϣ
	    String userDataId = userId;
	    int Count = TableManager.UserData.tableModel.getRowCount();
	    for(int i = 0; i < Count; i++) {
		    String rowId = (String) TableManager.UserData.tableModel.getValueAt(i, 0);
	    	if(rowId.equals(userDataId)) {
	    	    TableManager.UserData.tableModel.setValueAt(user.getUserName(), i, 1);
	    	    TableManager.UserData.tableModel.setValueAt(userData.getSex(), i, 2);
	    	    TableManager.UserData.tableModel.setValueAt(Integer.toString(userData.getAge()), i, 3);
	    	    TableManager.UserData.tableModel.setValueAt(userData.getPhoneNum(), i, 4);
	    	    TableManager.UserData.tableModel.setValueAt(userData.getEmail(), i, 5);
	    	    TableManager.UserData.tableModel.setValueAt(date, i, 6);
	    	    TableManager.UserData.tableModel.setValueAt(userData.getBlood(), i, 7);
	    	    TableManager.UserData.tableModel.setValueAt(userData.getSign(), i, 8);
	    	}
	    }
		
	}
	
	//��ѯ���ݿ���Ŀǰ���һ���˻����ʺ�,������һ�����õ�userIdд��message
	public Message setUserIdToMessage(Message message)
	{
		SqlHelper sqlHelper = new SqlHelper();
		String [] parameters = {};
		ResultSet resultSet = sqlHelper.searchSqlData(SqlCommandList.searchAllUser, parameters);

		int count = 0;
		try {
			while(resultSet.next()) { count = count + 1; }
			
			String userId = Integer.toString(count + 1);
			UserData userData = new UserData();
			User user = new User();
			userData.setPhoneNum(message.getUser().getUserData().getPhoneNum());
			user.setUserData(userData);
			user.setUserId(userId);
			user.setUserName(message.getUser().getUserName());
			user.setPassword(message.getUser().getPassword());
			message.setUser(user);
			
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return message;
	}

	//ͨ��id�����û����ϣ����ز��ҵ�����Ϣ
	public Message searchUserData(Message message) {
		
		Message messageReturn = new Message();
		UserData userData = new UserData();
		User user = message.getUser();
		
		String [] parameters = {};
		ResultSet resultSet = sqlHelper.searchSqlData(SqlCommandList.searchUserData, parameters);

		int id = Integer.parseInt(user.getUserId()) + 0;
		int row = 0;
		try {
			while(resultSet.next())
			{
				//if(resultSet.getObject(7)==null||"".equals(resultSet.getObject(7)))
				if(row+1 == id) {
					int userId = (int) resultSet.getObject(1);
					userData.setSex((String) resultSet.getObject(3));
					userData.setAge((int) resultSet.getObject(4));
					userData.setPhoneNum((String) resultSet.getObject(5));
					userData.setEmail((String) resultSet.getObject(6));
					userData.setBirthday((Date) resultSet.getObject(7));
					userData.setBlood((String) resultSet.getObject(8));
					userData.setSign((String) resultSet.getObject(9));
					user.setUserId(Integer.toString(userId));
					user.setUserName((String) resultSet.getObject(2));
					user.setUserData(userData);
					break;
				}row++;
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		messageReturn.setUser(user);
		messageReturn.setMessageType(MessageType.UserData);
		return messageReturn;
		
	}
	
	//���ص�ǰ���ݿ���ĺ�������
	public Message searchUserCount(Message message){
		Message messageReturn = new Message();
		String [] parameters = {};
		ResultSet resultSet = sqlHelper.searchSqlData(SqlCommandList.searchAllUser, parameters);
		
		try {
			int rowCount = 0;
			while(resultSet.next()){
				rowCount++; 
			}
			String rowcout=Integer.toString(rowCount);
			messageReturn.setMessage(rowcout);
			messageReturn.setMessageType(MessageType.GetuserCountSuccess);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return messageReturn;
	}
	
	//���������û�״̬�����������һ�ε�¼ʱ��
	public Message getLastOnlineTime(Message message){
		String username=null;
		String [] parameters = {};
		ResultSet resultSet = sqlHelper.searchSqlData(SqlCommandList.searchAllUser, parameters);
		try {
			//�û����ж�
			while(resultSet.next()){
				if(message.getUser().getUserId().equals(Integer.toString(resultSet.getInt(1)))){
					username=resultSet.getString(2);
				}
			}
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		
		String sqlOnlineUser="insert into �����û�(�ʺ�,����,�û���) values(";
		sqlOnlineUser=sqlOnlineUser+message.getUser().getUserId()+","+"'"+message.getUser().getPassword()+"'"+","+"'"+username+"'"+")";
		Message messageReturn = new Message();
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//���º�̨���ݱ��
			String [] onlineUser = {message.getUser().getUserId(), message.getUser().getPassword(), username}; 
			TableManager.onlineUser.tableModel.addRow(onlineUser);
			
			//���������û����
			sqlHelper.excuteData(sqlOnlineUser);
			Date time=dateFormat.parse(dateFormat.format(now));
			//�����ʺ�
			messageReturn.setMessage(message.getUser().getUserId());
			//��������¼ʱ��
			messageReturn.setSendTime(time);
			
		} catch (ParseException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return messageReturn;
	}
	//ɾ�������û�,����������¼ʱ��
	public void deleteOffLineUser(Message message){
		
		Date sqlDate;
		sqlDate = new java.sql.Date(message.getSendTime().getTime());
		String sqlInlineUser="update ȫ���û� set ����¼ʱ��= ";
		sqlInlineUser=sqlInlineUser+"'"+sqlDate+"'"+" where �ʺ� = "+"'"+message.getMessage()+"'";
	
	    String deleteOffLineUser="delete from �����û� where �ʺ� ="+"'"+message.getMessage()+"'";

		
	    //��������¼ʱ��
	    //ɾ�������û�
	    sqlHelper.excuteData(sqlInlineUser);
	    sqlHelper.excuteData(deleteOffLineUser);
	    
	    //�������û��е������û�����ɾ��
	    String offlineUserId = message.getMessage();
	    //int offlineuserID=Integer.parseInt(offlineUserId)-1;
	    //offlineUserId=Integer.toString(offlineuserID);
	    int count = TableManager.onlineUser.tableModel.getRowCount();
	    for(int i = 0; i < count; i++) {
		    String rowId = (String) TableManager.onlineUser.tableModel.getValueAt(i, 0);
	    	if(rowId.equals(offlineUserId)) {
	    	    TableManager.onlineUser.tableModel.removeRow(i);
	    	}
	    }
	    
	    //����ȫ���û����е�����¼ʱ��
	    String allUserId = message.getMessage();
	    int Count = TableManager.allUser.tableModel.getRowCount();
	    for(int i = 0; i < Count; i++) {
		    String rowId = (String) TableManager.allUser.tableModel.getValueAt(i, 0);
	    	if(rowId.equals(allUserId)) {
	    	    TableManager.allUser.tableModel.setValueAt(sqlDate, i, 3);
	    	}
	    }
	}
	//���û��޸�������д���
	public boolean changeUserPassword(Message message){
		boolean Success=false;
		User user = message.getUser();
		String username=null;
		String [] parameters = {};
		String sqlUpdateUserPassword="update ȫ���û� set ����= ";
		sqlUpdateUserPassword=sqlUpdateUserPassword+"'"+message.getMessage()+"'"+" where �ʺ� = "+user.getUserId();
		int rowCount=0;
		
		//����ȫ���û����е�����
	    String allUserId = user.getUserId();
	    int Count = TableManager.allUser.tableModel.getRowCount();
	    for(int i = 0; i < Count; i++) {
		    String rowId = (String) TableManager.allUser.tableModel.getValueAt(i, 0);
	    	if(rowId.equals(allUserId)) {
	    	    TableManager.allUser.tableModel.setValueAt(message.getMessage(), i, 2);
	    	}
	    }
		
	    //�������ݿ��е��û�����
		ResultSet resultSet = sqlHelper.searchSqlData(SqlCommandList.searchAllUser, parameters);
		
		try {
			while(resultSet.next()){
				if(rowCount!=resultSet.getRow()){
					if(resultSet.getInt(1)==Integer.parseInt(user.getUserId())){
						sqlHelper.excuteData(sqlUpdateUserPassword);
						Success=true;
						break;
					}
					rowCount++;
				}
				else{
					Success=false;
				}
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return Success;
	}
	
	//���������û��˺�
	public String returnOffLineUser(Message message){
		return message.getMessage();
	}
	
	//�û���������Ϣ�ж�
	public void judgeUserOnOffLine(Message message){
		if(message.getMessageType().equals(MessageType.UserOnline)){
			String meString="�û�"+message.getUser().getUserId()+"���ߣ�";
			Date now = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TableManager.logManagement.append(dateFormat.format(now)+" "+meString+"\n");
		}
		else if(message.getMessageType().equals(MessageType.UserOffLine)){
			String meString="�û�"+message.getMessage()+"���ߣ�";
			Date now = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TableManager.logManagement.append(dateFormat.format(now)+" "+meString+"\n");
		}
		else if(message.getMessageType().equals(MessageType.CommonChat)){
			String meString="�û�"+message.getSender() + "���û�" + message.getGetter() + "������Ϣ��" + message.getMessage();
			Date now = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TableManager.logManagement.append(dateFormat.format(now)+" "+meString+"\n");
		}
		else if(message.getMessageType().equals(MessageType.Register)){
			String meString="���û�ע��ɹ��� �˺ţ�"+message.getUser().getUserId();
			Date now = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TableManager.logManagement.append(dateFormat.format(now)+" "+meString+"\n");
		}
	}
}
