/**
 * �����������
 * */

package com.qq.common;

public interface MessageType 
{
	String LoginRequest = "1.0";//��¼�����	
	String LoginSuccess="1.1";//��ʾ��½�ɹ�	
	String LoginFail="1.2";//��ʾ��½ʧ��
	
	String CommonChat="2.0";//��ͨ������Ϣ��
	
	String GetOnlineFriend="3";//Ҫ�����ߺ��ѵİ�	
	String ReturnOnlineFriend="3.1";//�������ߺ���
	String GetuserCount="3.2";//�����û�����
	String GetuserCountSuccess="3.3";//�����û������ɹ�
	
	String UserData = "4.0";//�û�������Ϣ��	
	String UserDataUpdateSuccess = "4.1";//�û����ϸ��³ɹ�
	String UserDataUpdateFail = "4.2";//�û����ϸ���ʧ��
	String GetUserData = "4.3";//��ȡ�û�����
	String SetUserDataRequest="4.4";//�û����ø�����������
	String SetUserDataAllow="4.5";//�����û����ø�������
	
	String Register = "5.0";//�û�ע����Ϣ��
	String RegisterSuccess = "5.1";//�û�ע��ɹ�
	String RegisterFail = "5.2";//�û�ע��ʧ��
	
	String UserOnline="6.0";//�û�������Ϣ��
	String UserOffLine="6.1";//�û�������Ϣ��
	String OffLineSuccess="6.2";//�û��ɹ�����
	String NotifyOfflineUser="6.3";//֪ͨ�����û���������
	
	String GetLastLoginTime="7.0";//��ȡ����¼ʱ��
	
	String ChangePassword="8.0";//�û��޸�������Ϣ��
	String ChangePasswordSuccess="8.1";//�û��޸�����ɹ�
	String ChangePasswordFail="8.2";//�û��޸�����ʧ��
}
