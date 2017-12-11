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
	SqlHelper sqlHelper=new SqlHelper();
	public HandleClientMessage(){};
	//将注册信息添加到数据库，返回是否成功
	public boolean registerMessaage(Message message){
		boolean isCreateSqlalluser=false;
		boolean isCreateSqlUserData=false;
		
		String sqlalluser="insert into 全部用户(帐号,用户名,密码) values(";
		sqlalluser=sqlalluser+message.getUser().getUserId()+","+"'"+message.getUser().getUserName()+"'"+","+"'"+message.getUser().getPassword()+"'"+")";
		System.out.println(sqlalluser);
		
		String sqlUserData="insert into 用户资料(帐号,用户名,手机号) values(";
		sqlUserData=sqlUserData+message.getUser().getUserId()+","+"'"+message.getUser().getUserName()+"'"+","+"'"+message.getUser().getUserData().getPhoneNum()+"'"+")";
		System.out.println(sqlUserData);
		
		//表格添加行
		
		//数据库更新
		sqlHelper.excuteData(sqlalluser);
		isCreateSqlalluser = true;
		//表格添加行
		
		//数据库更新
		sqlHelper.excuteData(sqlUserData);
		isCreateSqlUserData = true;
		
		if(isCreateSqlalluser && isCreateSqlUserData) 
			return true;
		
		return false;
	}
	
	//更新数据库中的个人资料
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
		
		//更新个人资料
		sqlHelper.updateData(SqlCommandList.updateUserDataUserName, userName);
		sqlHelper.updateData(SqlCommandList.updateUserDataSex, sex);
		sqlHelper.updateData(SqlCommandList.updateUserDataAge, age);
		sqlHelper.updateData(SqlCommandList.updateUserDataPhoneNum, phoneNum);
		sqlHelper.updateData(SqlCommandList.updateUserDataEmail, email);
		sqlHelper.updateData(SqlCommandList.updateUserDataBirthday, birthday);
		sqlHelper.updateData(SqlCommandList.updateUserDataBlood, blood);
		sqlHelper.updateData(SqlCommandList.updateUserDataSign, sign);
		
/*		String [] paras = {
				user.getUserName(), 
				userData.getSex(), 
				Integer.toString(userData.getAge()), 
				userData.getPhoneNum(),
				userData.getEmail(),
				date,
				userData.getBlood(),
				userData.getSign(),
				userId
				};
		sqlHelper.updateData(SqlCommandList.updateUserData, paras);*/
		
	}
	
	//查询数据库中目前最后一个账户的帐号,并将下一个可用的userId写入message
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return message;
	}

	//通过id查找用户资料，返回查找到的信息
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		messageReturn.setUser(user);
		messageReturn.setMessageType(MessageType.UserData);
		return messageReturn;
		
	}
	
	//返回当前数据库里的好友数量
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return messageReturn;
	}
	
	//插入在线用户状态，并返回最后一次登录时间
	public Message getLastOnlineTime(Message message){
		String username=null;
		String [] parameters = {};
		ResultSet resultSet = sqlHelper.searchSqlData(SqlCommandList.searchAllUser, parameters);
		try {
			//用户名判断
			while(resultSet.next()){
				if(message.getUser().getUserId().equals(Integer.toString(resultSet.getInt(1)))){
					username=resultSet.getString(2);
				}
			}
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		
		String sqlOnlineUser="insert into 在线用户(帐号,密码,用户名) values(";
		sqlOnlineUser=sqlOnlineUser+message.getUser().getUserId()+","+"'"+message.getUser().getPassword()+"'"+","+"'"+username+"'"+")";
		Message messageReturn = new Message();
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//更新后台数据表格
			
			//插入在线用户表格
			sqlHelper.excuteData(sqlOnlineUser);
			Date time=dateFormat.parse(dateFormat.format(now));
			//返回帐号
			messageReturn.setMessage(message.getUser().getUserId());
			//返回最后登录时间
			messageReturn.setSendTime(time);
			
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return messageReturn;
	}
	//删除离线用户,并更新最后登录时间
	public void deleteOffLineUser(Message message){
		
		Date sqlDate;
		sqlDate = new java.sql.Date(message.getSendTime().getTime());
		String sqlInlineUser="update 全部用户 set 最后登录时间= ";
		sqlInlineUser=sqlInlineUser+"'"+sqlDate+"'"+" where 帐号 = "+"'"+message.getMessage()+"'";
	
	    String deleteOffLineUser="delete from 在线用户 where 帐号 ="+"'"+message.getMessage()+"'";
	
	    //更新最后登录时间
	    sqlHelper.excuteData(sqlInlineUser);
	    
	    //删除离线用户
	    sqlHelper.excuteData(deleteOffLineUser);
	}
	//对用户修改密码进行处理
	public boolean changeUserPassword(Message message){
		boolean Success=false;
		User user = message.getUser();
		String username=null;
		String [] parameters = {};
		String sqlUpdateUserPassword="update 全部用户 set 密码= ";
		sqlUpdateUserPassword=sqlUpdateUserPassword+"'"+message.getMessage()+"'"+" where 帐号 = "+user.getUserId();
		int rowCount=0;
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return Success;
	}
	
}
