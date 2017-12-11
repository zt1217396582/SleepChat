package com.qq.service.db;

//���õ���sql���
public interface SqlCommandList {

	//��ѯ���
	String searchOnlineUser = "select * from �����û�";
	String searchAllUser = "select * from ȫ���û�";
	String searchUserData = "select * from �û�����";
	String searchAdministrator = "select * from ����Ա�ʻ�";
	
	//�������
	String addOnlineUser="insert into �����û�(�ʺ�,����,�û���) ";
	String addAllUser="insert into ȫ���û�(�ʺ�,����,�û���) ";
	String addUserData="insert into �û�����(�ʺ�,����,�û���,�Ա�,����,�ֻ���,����,����) ";
	String addAdministrator="insert into ����Ա�ʻ�(����Ա�ʺ�,����Ա����) ";
	
	//�������
	String updateOnlineUser1="update �����û� set �û��� = ? where �ʺ� = ?";
	String updateOnlineUser2="update �����û� set ����= ? where �ʺ� = ?";
	
	String updateAllUser1="update �����û� set �û��� = ? where �ʺ� = ?";
	String updateAllUser2="update �����û� set ���� = ? where �ʺ� = ?";
	
	String updateUserData="update �û����� set �û���= ? "
			+ "set �Ա� = ? set ���� = ? set �ֻ��� = ? "
			+ "set ���� = ? set ���� = ? set Ѫ�� = ? set ����ǩ�� = ? "
			+ "where �ʺ� = ?";
	String updateUserDataUserName="update �û����� set �û���= ? where �ʺ� = ?";
	String updateUserDataPasswd="update �û����� set ���� = ? where �ʺ� = ?";
	String updateUserDataSex="update �û����� set �Ա� = ? where �ʺ� = ?";
	String updateUserDataAge="update �û����� set ���� = ? where �ʺ� = ?";
	String updateUserDataPhoneNum="update �û����� set �ֻ��� = ? where �ʺ� = ?";
	String updateUserDataEmail="update �û����� set ���� = ? where �ʺ� = ?";
	String updateUserDataBirthday="update �û����� set ���� = ? where �ʺ� = ?";
	String updateUserDataBlood="update �û����� set Ѫ�� = ? where �ʺ� = ?";
	String updateUserDataSign="update �û����� set ����ǩ�� = ? where �ʺ� = ?";
	
	String updateAdministrator="update ����Ա�ʻ�  set ����Ա���� =? where ����Ա�ʺ� =?";
	
	//ɾ�����
	String deleteAdministrator="delete from ����Ա�ʻ� where ����Ա�ʺ� =?";
	
}
