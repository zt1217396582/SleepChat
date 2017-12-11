package com.qq.service.db;

//会用到的sql语句
public interface SqlCommandList {

	//查询语句
	String searchOnlineUser = "select * from 在线用户";
	String searchAllUser = "select * from 全部用户";
	String searchUserData = "select * from 用户资料";
	String searchAdministrator = "select * from 管理员帐户";
	
	//插入语句
	String addOnlineUser="insert into 在线用户(帐号,密码,用户名) ";
	String addAllUser="insert into 全部用户(帐号,密码,用户名) ";
	String addUserData="insert into 用户资料(帐号,密码,用户名,性别,年龄,手机号,邮箱,生日) ";
	String addAdministrator="insert into 管理员帐户(管理员帐号,管理员密码) ";
	
	//更新语句
	String updateOnlineUser1="update 在线用户 set 用户名 = ? where 帐号 = ?";
	String updateOnlineUser2="update 在线用户 set 密码= ? where 帐号 = ?";
	
	String updateAllUser1="update 在线用户 set 用户名 = ? where 帐号 = ?";
	String updateAllUser2="update 在线用户 set 密码 = ? where 帐号 = ?";
	
	String updateUserData="update 用户资料 set 用户名= ? "
			+ "set 性别 = ? set 年龄 = ? set 手机号 = ? "
			+ "set 邮箱 = ? set 生日 = ? set 血型 = ? set 个性签名 = ? "
			+ "where 帐号 = ?";
	String updateUserDataUserName="update 用户资料 set 用户名= ? where 帐号 = ?";
	String updateUserDataPasswd="update 用户资料 set 密码 = ? where 帐号 = ?";
	String updateUserDataSex="update 用户资料 set 性别 = ? where 帐号 = ?";
	String updateUserDataAge="update 用户资料 set 年龄 = ? where 帐号 = ?";
	String updateUserDataPhoneNum="update 用户资料 set 手机号 = ? where 帐号 = ?";
	String updateUserDataEmail="update 用户资料 set 邮箱 = ? where 帐号 = ?";
	String updateUserDataBirthday="update 用户资料 set 生日 = ? where 帐号 = ?";
	String updateUserDataBlood="update 用户资料 set 血型 = ? where 帐号 = ?";
	String updateUserDataSign="update 用户资料 set 个性签名 = ? where 帐号 = ?";
	
	String updateAdministrator="update 管理员帐户  set 管理员密码 =? where 管理员帐号 =?";
	
	//删除语句
	String deleteAdministrator="delete from 管理员帐户 where 管理员帐号 =?";
	
}
