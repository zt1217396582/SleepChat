/**
 * qq����������������ĳ���ͻ��˵�����
 */

package com.qq.service.model;

import com.qq.service.controller.ServiceThread;

public class MyQqService {
	
	public MyQqService()
	{
		Thread serviceThread = new ServiceThread();
		serviceThread.start();
	}

}
